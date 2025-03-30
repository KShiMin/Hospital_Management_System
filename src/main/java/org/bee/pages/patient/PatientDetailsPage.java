package org.bee.pages.patient;

import org.bee.hms.humans.Patient;
import org.bee.pages.GenericUpdatePage;
import org.bee.ui.Color;
import org.bee.ui.SystemMessageStatus;
import org.bee.ui.UiBase;
import org.bee.ui.View;
import org.bee.ui.views.CompositeView;
import org.bee.ui.views.MenuView;
import org.bee.ui.views.ObjectDetailsView;
import org.bee.ui.views.TextView;
import org.bee.utils.detailAdapters.PatientDetailsAdapter;
import org.bee.utils.formAdapters.PatientFormAdapter;

import java.util.Objects;

/**
 * A page that displays detailed information about a {@link Patient} object.
 * <p>
 * The page includes a detail section and a menu to allow editing of patient information.
 * It uses the {@link PatientDetailsAdapter} for rendering and {@link PatientFormAdapter} for updating.
 */
public class PatientDetailsPage extends UiBase {

    private final Patient patient;
    private final PatientDetailsAdapter detailsAdapter = new PatientDetailsAdapter();

    /**
     * Constructs a new {@code PatientDetailsPage} for the specified patient.
     *
     * @param patient the {@link Patient} whose details will be displayed
     */
    public PatientDetailsPage(Patient patient) {
        this.patient = patient;
    }

    /**
     * Creates the main view displaying the patient details and action menu.
     *
     * @return a {@link View} showing the patient's information and edit option
     */
    @Override
    public View createView() {
        if (Objects.isNull(patient)) {
            return new TextView(this.canvas, "Error: No patient selected", Color.RED);
        }

        CompositeView compositeView = new CompositeView(this.canvas, "Patient Details", Color.CYAN);

        ObjectDetailsView detailsView = new ObjectDetailsView(
                this.canvas,
                "Patient Details",
                patient,
                Color.CYAN
        );

        detailsAdapter.configureView(detailsView, patient);

        MenuView menuView = new MenuView(this.canvas, "", Color.CYAN, false, true);
        menuView.attachMenuOptionInput(1, "Edit Patient Details", input -> editPatient());

        compositeView.addView(detailsView);
        compositeView.addView(menuView);

        return compositeView;
    }

    /**
     * Called after the view is rendered to the screen.
     * Forces a UI redraw to ensure consistency.
     *
     * @param parentView the view that was just created
     */
    @Override
    public void OnViewCreated(View parentView) {
        canvas.setRequireRedraw(true);
    }

    /**
     * Handles editing the current patient's details.
     * Launches a {@link GenericUpdatePage} with a {@link PatientFormAdapter} and refreshes the view on success.
     */
    private void editPatient() {
        try {
            PatientFormAdapter formAdapter = new PatientFormAdapter();

            GenericUpdatePage<Patient> updatePage = new GenericUpdatePage<>(
                    patient,
                    formAdapter,
                    () -> {
                        View refreshedView = createView();
                        navigateToView(refreshedView);
                        canvas.setSystemMessage("Patient updated successfully", SystemMessageStatus.SUCCESS);
                    }
            );

            ToPage(updatePage);
        } catch (Exception e) {
            canvas.setSystemMessage("Error opening update form: " + e.getMessage(), SystemMessageStatus.ERROR);
            canvas.setRequireRedraw(true);
        }
    }
}

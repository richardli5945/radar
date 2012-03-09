package com.solidstategroup.radar.web.pages.login;

import com.solidstategroup.radar.model.user.PatientUser;
import com.solidstategroup.radar.service.UserManager;
import com.solidstategroup.radar.web.RadarSecuredSession;
import com.solidstategroup.radar.web.components.RadarRequiredDateTextField;
import com.solidstategroup.radar.web.components.RadarRequiredPasswordTextField;
import com.solidstategroup.radar.web.components.RadarRequiredTextField;
import com.solidstategroup.radar.web.pages.BasePage;
import com.solidstategroup.radar.web.pages.PatientPageReadOnly;
import com.solidstategroup.radar.web.pages.regisration.PatientRegistrationPage;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

public class PatientsLoginPage extends BasePage {

    public static final String LOGIN_FAILED_MESSAGE = "Login failed";
    @SpringBean
    private UserManager userManager;

    public PatientsLoginPage() {
        // Patients log in form
        CompoundPropertyModel<PatientUser> model = new CompoundPropertyModel<PatientUser>(new PatientUser());
        final Model<String> passwordModel = new Model<String>();

        // components to update on ajax
        final List<Component> componentsToUpdateList = new ArrayList<Component>();

        Form<PatientUser> form = new Form<PatientUser>("form", model) {
            @Override
            protected void onSubmit() {
                RadarSecuredSession session = RadarSecuredSession.get();
                PatientUser user = getModelObject();
                boolean loginFailed = false;
                PatientUser patientUser = userManager.getPatientUser(user.getUsername(), user.getDateOfBirth());
                if (patientUser != null) {
                    if (session.signIn(user.getUsername(), passwordModel.getObject())) {
                        session.setUser(patientUser);
                        // If we haven't been diverted here from a page request (i.e. we clicked login),
                        // redirect to logged in page
                        setResponsePage(PatientPageReadOnly.class, PatientPageReadOnly.getParameters(
                                patientUser.getRadarNumber()));

                    } else {
                        loginFailed = true;
                    }
                } else {
                    loginFailed = true;
                }

                if (loginFailed) {
                    // Show that the login failed if we couldn't authenticate
                    error(LOGIN_FAILED_MESSAGE);
                }
            }
        };
        add(form);

        // Add components to form
        form.add(new RadarRequiredTextField("username", form, componentsToUpdateList));
        RadarRequiredPasswordTextField password = new RadarRequiredPasswordTextField("password", form,
                componentsToUpdateList);
        form.add(password);
        password.setModel(passwordModel);

        // Date of birth with picker
        DateTextField dateOfBirth = new RadarRequiredDateTextField("dateOfBirth",
                form, componentsToUpdateList);
        form.add(dateOfBirth);

        // Construct feedback panel
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback", new IFeedbackMessageFilter() {
            public boolean accept(FeedbackMessage feedbackMessage) {
                String message = feedbackMessage.getMessage().toString();
                return message.contains(LOGIN_FAILED_MESSAGE);
            }
        });
        form.add(feedbackPanel);
        componentsToUpdateList.add(feedbackPanel);
        feedbackPanel.setOutputMarkupPlaceholderTag(true);

        form.add(new IndicatingAjaxButton("submit") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                target.add(componentsToUpdateList.toArray(new Component[componentsToUpdateList.size()]));
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(componentsToUpdateList.toArray(new Component[componentsToUpdateList.size()]));
            }
        });


        // Add links for forgotten password and register
        add(new BookmarkablePageLink<PatientForgottenPasswordPage>("forgottenPasswordLink",
                PatientForgottenPasswordPage.class));
        add(new BookmarkablePageLink<PatientRegistrationPage>("registerLink", PatientRegistrationPage.class));
    }
}

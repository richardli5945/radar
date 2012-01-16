package com.solidstategroup.radar.web.panels.firstvisit;

import com.solidstategroup.radar.dao.ClinicalDataDao;
import com.solidstategroup.radar.dao.DemographicsDao;
import com.solidstategroup.radar.dao.DiagnosisDao;
import com.solidstategroup.radar.model.sequenced.ClinicalData;
import com.solidstategroup.radar.web.components.PhenotypeChooser;
import com.solidstategroup.radar.web.components.RadarRequiredDateTextField;
import com.solidstategroup.radar.web.components.RadarTextFieldWithValidation;
import com.solidstategroup.radar.web.components.YesNoRadioGroup;
import com.solidstategroup.radar.web.models.RadarModelFactory;
import com.solidstategroup.radar.web.panels.FirstVisitPanel;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.RangeValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClinicalPicturePanel extends Panel {

    @SpringBean
    private ClinicalDataDao clinicalDataDao;
    @SpringBean
    private DemographicsDao demographicsDao;
    @SpringBean
    private DiagnosisDao diagnosisDao;

    public ClinicalPicturePanel(String id, final IModel<Long> radarNumberModel) {
        super(id);
        setOutputMarkupId(true);
        setOutputMarkupPlaceholderTag(true);

        final TextField<Double> diastolicBloodPressure = new TextField<Double>("diastolicBloodPressure");

        CompoundPropertyModel<ClinicalData> model =
                new CompoundPropertyModel<ClinicalData>(new LoadableDetachableModel<ClinicalData>() {
                    @Override
                    protected ClinicalData load() {
                        if (radarNumberModel.getObject() != null) {
                            // If we have a radar number get the list from DAO
                            List<ClinicalData> clinicalDatas;
                            try {
                                 clinicalDatas = clinicalDataDao.getClinicalDataByRadarNumber(radarNumberModel.getObject());
                            } catch(ClassCastException e) {
                                Object obj = radarNumberModel.getObject();
                               clinicalDatas = clinicalDataDao.getClinicalDataByRadarNumber((
                                       Long.parseLong((String)obj)));
                            }

                            if (!clinicalDatas.isEmpty()) {
                                // This is first visit so return the first
                                return clinicalDatas.get(0);
                            }
                        }
                        // By default just return new one
                        return new ClinicalData();
                    }
                });


        final Form<ClinicalData> form = new Form<ClinicalData>("form", model) {
            @Override
            protected void onValidateModelObjects() {
                super.onValidateModelObjects();
                ClinicalData clinicalData = getModelObject();
                Integer systolicBloodPressureVal = clinicalData.getSystolicBloodPressure();
                Integer diastolicBloodPressureVal = clinicalData.getDiastolicBloodPressure();
                if (systolicBloodPressureVal != null && diastolicBloodPressureVal != null) {
                    if (!(systolicBloodPressureVal.compareTo(diastolicBloodPressureVal) > 0)) {
                        diastolicBloodPressure.error("This value has to be less than the first value");
                    }
                }

            }
        };

        final List<Component> componentsToUpdate = new ArrayList<Component>();

        form.add(new TextField("radarNumber", radarNumberModel));

        form.add(new TextField("hospitalNumber", RadarModelFactory.getHospitalNumberModel(radarNumberModel,
                demographicsDao)));



        form.add(new TextField("diagnosis", new PropertyModel(RadarModelFactory.getDiagnosisCodeModel(radarNumberModel,
                diagnosisDao), "abbreviation")));

        form.add(new TextField("firstName", RadarModelFactory.getFirstNameModel(radarNumberModel, demographicsDao)));

        form.add(new TextField("surname", RadarModelFactory.getSurnameModel(radarNumberModel, demographicsDao)));


        form.add(new TextField("dob", RadarModelFactory.getDobModel(radarNumberModel, demographicsDao)));

        RadarRequiredDateTextField clinicalPictureDate =
                new RadarRequiredDateTextField("clinicalPictureDate", new Model<Date>(), form, componentsToUpdate);
        form.add(clinicalPictureDate);

        RadarTextFieldWithValidation height =
                new RadarTextFieldWithValidation("height", new RangeValidator<Double>(35.0, 185.0), form,
                        componentsToUpdate);
        form.add(height);

        RadarTextFieldWithValidation weight =
                new RadarTextFieldWithValidation("weight", new RangeValidator<Double>(3.0, 100.0), form,
                        componentsToUpdate);
        form.add(weight);
        // Blood pressure
        TextField<Double> systolicBloodPressure = new TextField("systolicBloodPressure");
        systolicBloodPressure.add(new RangeValidator<Integer>(50, 200));
        form.add(systolicBloodPressure);

        final ComponentFeedbackPanel systolicBloodPressureFeedback =
                new ComponentFeedbackPanel("systolicBloodPressureFeedback", systolicBloodPressure);
        systolicBloodPressureFeedback.setOutputMarkupId(true);
        systolicBloodPressureFeedback.setOutputMarkupPlaceholderTag(true);
        form.add(systolicBloodPressureFeedback);

        diastolicBloodPressure.add(new RangeValidator<Integer>(20, 150));
        form.add(diastolicBloodPressure);

        final ComponentFeedbackPanel diastolicBloodPressureFeedback =
                new ComponentFeedbackPanel("diastolicBloodPressureFeedback", diastolicBloodPressure);
        diastolicBloodPressureFeedback.setOutputMarkupId(true);
        diastolicBloodPressureFeedback.setOutputMarkupPlaceholderTag(true);
        form.add(diastolicBloodPressureFeedback);

        form.add(new TextField("meanArterialPressure").setEnabled(false));

        form.add(new PhenotypeChooser("phenotype1"));
        form.add(new PhenotypeChooser("phenotype2"));
        form.add(new PhenotypeChooser("phenotype3"));
        form.add(new PhenotypeChooser("phenotype4"));

        form.add(new TextArea("comments"));
        form.add(new TextField("significantDiagnosis1"));
        form.add(new TextField("significantDiagnosis2"));

        // Yes/No/Unknown for the following
        form.add(new YesNoRadioGroup("oedema", true));
        form.add(new YesNoRadioGroup("hypovalaemia", true));
        form.add(new YesNoRadioGroup("fever", true));
        form.add(new YesNoRadioGroup("thrombosis", true));
        form.add(new YesNoRadioGroup("peritonitis", true));
        form.add(new YesNoRadioGroup("pulmonaryOedema", true));
        form.add(new YesNoRadioGroup("hypertension", true));

        // Diabetes
        form.add(new DropDownChoice<ClinicalData.DiabetesType>("diabetesType"));

        // Todo: Visibilities depend on the diagnosis - see first-clinical.aspx.vb:287

        // More yes/no options
        form.add(new YesNoRadioGroup("rash", true));
        form.add(new YesNoRadioGroup("possibleImmunisationTrigger", true));
        form.add(new YesNoRadioGroup("partialLipodystrophy", true));
        form.add(new YesNoRadioGroup("preceedingInfection", true));
        form.add(new YesNoRadioGroup("chronicInfection", true));
        form.add(new YesNoRadioGroup("ophthalmoscopy", true));

        // Rash details needs show/hide
        MarkupContainer rashDetailContainer = new WebMarkupContainer("rashDetailContainer") {
            @Override
            public boolean isVisible() {
                return Boolean.TRUE.equals(form.getModelObject().getRash());
            }
        };
        rashDetailContainer.add(new TextArea("rashDetail"));
        form.add(rashDetailContainer);

        // Ophthalmoscopy show/hide
        MarkupContainer ophthalmoscopyDetailContainer = new WebMarkupContainer("ophthalmoscopyDetailContainer") {
            @Override
            public boolean isVisible() {
                return Boolean.TRUE.equals(form.getModelObject().getOphthalmoscopy());
            }
        };
        ophthalmoscopyDetailContainer.add(new TextArea("ophthalmoscopyDetail"));
        form.add(ophthalmoscopyDetailContainer);

        // Preceeding infection show/hide
        MarkupContainer preceedingInfectionDetailContainer =
                new WebMarkupContainer("preceedingInfectionDetailContainer") {
                    @Override
                    public boolean isVisible() {
                        return Boolean.TRUE.equals(form.getModelObject().getPreceedingInfection());
                    }
                };
        preceedingInfectionDetailContainer.add(new TextArea("preceedingInfectionDetail"));
        form.add(preceedingInfectionDetailContainer);

        // Chronic infection show/hide
        MarkupContainer chronicInfectionDetailContainer = new WebMarkupContainer("chronicInfectionDetailContainer") {
            @Override
            public boolean isVisible() {
                return Boolean.TRUE.equals(form.getModelObject().getChronicInfection());
            }
        };
        ophthalmoscopyDetailContainer.add(new TextArea("chronicInfectionDetail"));
        form.add(chronicInfectionDetailContainer);

        add(form);

        componentsToUpdate.add(systolicBloodPressureFeedback);
        componentsToUpdate.add(diastolicBloodPressureFeedback);

        ClinicalAjaxSubmitLink save = new ClinicalAjaxSubmitLink("save") {
            @Override
            protected List<? extends Component> getComponentsToUpdate() {
                return componentsToUpdate;
            }
        };

        ClinicalAjaxSubmitLink saveDown = new ClinicalAjaxSubmitLink("saveDown") {
            @Override
            protected List<? extends Component> getComponentsToUpdate() {
                return componentsToUpdate;
            }
        };

        form.add(save, saveDown);
    }

    @Override
    public boolean isVisible() {
        return ((FirstVisitPanel) getParent()).getCurrentTab().equals(FirstVisitPanel.CurrentTab.CLINICAL_PICTURE);
    }

    private abstract class ClinicalAjaxSubmitLink extends AjaxSubmitLink {

        public ClinicalAjaxSubmitLink(String id) {
            super(id);
        }

        @Override
        public void onSubmit(AjaxRequestTarget target, Form<?> form) {
            target.add(getComponentsToUpdate().toArray(new Component[getComponentsToUpdate().size()]));
        }

        @Override
        protected void onError(AjaxRequestTarget target, Form<?> form) {
            target.add(getComponentsToUpdate().toArray(new Component[getComponentsToUpdate().size()]));
        }

        protected abstract List<? extends Component> getComponentsToUpdate();
    }

}

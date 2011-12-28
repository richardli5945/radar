package com.solidstategroup.radar.model;

import java.util.Date;

/**
 * Corresponds to the tbl_Diagnosis table within the database.
 */
public class Diagnosis extends BaseModel {

    public enum YesNo {
        YES, NO, UNKNOWN
    }

    public enum MutationYorN {
        Y, N
    }

    public enum MutationSorSN {
        S, SN
    }

    public enum SteroidResistance {
        PRIMARY(1), SECONDARY(2), PRESUMED(3), BPS(4);

        private int id;

        SteroidResistance(int id) {
            this.id = id;
        }
    }

    private DiagnosisCode diagnosisCode;
    private String text;
    private Date biopsyDate;
    private Date esrfDate;
    private int ageAtDiagnosis;
    private boolean prepubertalAtDiagnosis;
    private double heightAtDiagnosis; // In cm

    private ClinicalPresentation clinicalPresentationA, clinicalPresentationB;
    private SteroidResistance steroidResistance;

    private Date onsetSymptomsDate;

    private String significantDiagnosis1, significantDiagnosis2;

    private YesNo biopsyProvenDiagnosis; // BX_PROVEN_DIAG in database

    private MutationYorN mutationYorN1, mutationYorN2, mutationYorN3, mutationYorN4, mutationYorN5, mutationYorN6,
            mutationYorN7, mutationYorN8, mutationYorN9;
    private MutationSorSN mutationSorSN1, mutationSorSN2, mutationSorSN3, mutationSorSN4, mutationSorSN5,
            mutationSorSN6, mutationSorSN7, mutationSorSN8, mutationSorSN9;

    private Karotype karotype;
    private YesNo parentalConsanguinity;
    private YesNo familyHistory;

    public DiagnosisCode getDiagnosisCode() {
        return diagnosisCode;
    }

    public void setDiagnosisCode(DiagnosisCode diagnosisCode) {
        this.diagnosisCode = diagnosisCode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getBiopsyDate() {
        return biopsyDate;
    }

    public void setBiopsyDate(Date biopsyDate) {
        this.biopsyDate = biopsyDate;
    }

    public Date getEsrfDate() {
        return esrfDate;
    }

    public void setEsrfDate(Date esrfDate) {
        this.esrfDate = esrfDate;
    }

    public int getAgeAtDiagnosis() {
        return ageAtDiagnosis;
    }

    public void setAgeAtDiagnosis(int ageAtDiagnosis) {
        this.ageAtDiagnosis = ageAtDiagnosis;
    }

    public boolean isPrepubertalAtDiagnosis() {
        return prepubertalAtDiagnosis;
    }

    public void setPrepubertalAtDiagnosis(boolean prepubertalAtDiagnosis) {
        this.prepubertalAtDiagnosis = prepubertalAtDiagnosis;
    }

    public double getHeightAtDiagnosis() {
        return heightAtDiagnosis;
    }

    public void setHeightAtDiagnosis(double heightAtDiagnosis) {
        this.heightAtDiagnosis = heightAtDiagnosis;
    }

    public ClinicalPresentation getClinicalPresentationA() {
        return clinicalPresentationA;
    }

    public void setClinicalPresentationA(ClinicalPresentation clinicalPresentationA) {
        this.clinicalPresentationA = clinicalPresentationA;
    }

    public ClinicalPresentation getClinicalPresentationB() {
        return clinicalPresentationB;
    }

    public void setClinicalPresentationB(ClinicalPresentation clinicalPresentationB) {
        this.clinicalPresentationB = clinicalPresentationB;
    }

    public SteroidResistance getSteroidResistance() {
        return steroidResistance;
    }

    public void setSteroidResistance(SteroidResistance steroidResistance) {
        this.steroidResistance = steroidResistance;
    }

    public Date getOnsetSymptomsDate() {
        return onsetSymptomsDate;
    }

    public void setOnsetSymptomsDate(Date onsetSymptomsDate) {
        this.onsetSymptomsDate = onsetSymptomsDate;
    }

    public String getSignificantDiagnosis1() {
        return significantDiagnosis1;
    }

    public void setSignificantDiagnosis1(String significantDiagnosis1) {
        this.significantDiagnosis1 = significantDiagnosis1;
    }

    public String getSignificantDiagnosis2() {
        return significantDiagnosis2;
    }

    public void setSignificantDiagnosis2(String significantDiagnosis2) {
        this.significantDiagnosis2 = significantDiagnosis2;
    }

    public YesNo getBiopsyProvenDiagnosis() {
        return biopsyProvenDiagnosis;
    }

    public void setBiopsyProvenDiagnosis(YesNo biopsyProvenDiagnosis) {
        this.biopsyProvenDiagnosis = biopsyProvenDiagnosis;
    }

    public MutationYorN getMutationYorN1() {
        return mutationYorN1;
    }

    public void setMutationYorN1(MutationYorN mutationYorN1) {
        this.mutationYorN1 = mutationYorN1;
    }

    public MutationYorN getMutationYorN2() {
        return mutationYorN2;
    }

    public void setMutationYorN2(MutationYorN mutationYorN2) {
        this.mutationYorN2 = mutationYorN2;
    }

    public MutationYorN getMutationYorN3() {
        return mutationYorN3;
    }

    public void setMutationYorN3(MutationYorN mutationYorN3) {
        this.mutationYorN3 = mutationYorN3;
    }

    public MutationYorN getMutationYorN4() {
        return mutationYorN4;
    }

    public void setMutationYorN4(MutationYorN mutationYorN4) {
        this.mutationYorN4 = mutationYorN4;
    }

    public MutationYorN getMutationYorN5() {
        return mutationYorN5;
    }

    public void setMutationYorN5(MutationYorN mutationYorN5) {
        this.mutationYorN5 = mutationYorN5;
    }

    public MutationYorN getMutationYorN6() {
        return mutationYorN6;
    }

    public void setMutationYorN6(MutationYorN mutationYorN6) {
        this.mutationYorN6 = mutationYorN6;
    }

    public MutationYorN getMutationYorN7() {
        return mutationYorN7;
    }

    public void setMutationYorN7(MutationYorN mutationYorN7) {
        this.mutationYorN7 = mutationYorN7;
    }

    public MutationYorN getMutationYorN8() {
        return mutationYorN8;
    }

    public void setMutationYorN8(MutationYorN mutationYorN8) {
        this.mutationYorN8 = mutationYorN8;
    }

    public MutationYorN getMutationYorN9() {
        return mutationYorN9;
    }

    public void setMutationYorN9(MutationYorN mutationYorN9) {
        this.mutationYorN9 = mutationYorN9;
    }

    public MutationSorSN getMutationSorSN1() {
        return mutationSorSN1;
    }

    public void setMutationSorSN1(MutationSorSN mutationSorSN1) {
        this.mutationSorSN1 = mutationSorSN1;
    }

    public MutationSorSN getMutationSorSN2() {
        return mutationSorSN2;
    }

    public void setMutationSorSN2(MutationSorSN mutationSorSN2) {
        this.mutationSorSN2 = mutationSorSN2;
    }

    public MutationSorSN getMutationSorSN3() {
        return mutationSorSN3;
    }

    public void setMutationSorSN3(MutationSorSN mutationSorSN3) {
        this.mutationSorSN3 = mutationSorSN3;
    }

    public MutationSorSN getMutationSorSN4() {
        return mutationSorSN4;
    }

    public void setMutationSorSN4(MutationSorSN mutationSorSN4) {
        this.mutationSorSN4 = mutationSorSN4;
    }

    public MutationSorSN getMutationSorSN5() {
        return mutationSorSN5;
    }

    public void setMutationSorSN5(MutationSorSN mutationSorSN5) {
        this.mutationSorSN5 = mutationSorSN5;
    }

    public MutationSorSN getMutationSorSN6() {
        return mutationSorSN6;
    }

    public void setMutationSorSN6(MutationSorSN mutationSorSN6) {
        this.mutationSorSN6 = mutationSorSN6;
    }

    public MutationSorSN getMutationSorSN7() {
        return mutationSorSN7;
    }

    public void setMutationSorSN7(MutationSorSN mutationSorSN7) {
        this.mutationSorSN7 = mutationSorSN7;
    }

    public MutationSorSN getMutationSorSN8() {
        return mutationSorSN8;
    }

    public void setMutationSorSN8(MutationSorSN mutationSorSN8) {
        this.mutationSorSN8 = mutationSorSN8;
    }

    public MutationSorSN getMutationSorSN9() {
        return mutationSorSN9;
    }

    public void setMutationSorSN9(MutationSorSN mutationSorSN9) {
        this.mutationSorSN9 = mutationSorSN9;
    }

    public Karotype getKarotype() {
        return karotype;
    }

    public void setKarotype(Karotype karotype) {
        this.karotype = karotype;
    }

    public YesNo getParentalConsanguinity() {
        return parentalConsanguinity;
    }

    public void setParentalConsanguinity(YesNo parentalConsanguinity) {
        this.parentalConsanguinity = parentalConsanguinity;
    }

    public YesNo getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(YesNo familyHistory) {
        this.familyHistory = familyHistory;
    }
}
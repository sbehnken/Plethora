
package com.example.bogglegame.bogglegame.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sense {

    @SerializedName("definitions")
    @Expose
    private List<String> definitions = null;
    @SerializedName("examples")
    @Expose
    private List<Example> examples = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("short_definitions")
    @Expose
    private List<String> shortDefinitions = null;
    @SerializedName("subsenses")
    @Expose
    private List<Subsense> subsenses = null;
    @SerializedName("thesaurusLinks")
    @Expose
    private List<ThesaurusLink_> thesaurusLinks = null;
    @SerializedName("crossReferences")
    @Expose
    private List<CrossReference> crossReferences = null;
    @SerializedName("domains")
    @Expose
    private List<String> domains = null;
    @SerializedName("registers")
    @Expose
    private List<String> registers = null;
    @SerializedName("variantForms")
    @Expose
    private List<VariantForm> variantForms = null;
    @SerializedName("crossReferenceMarkers")
    @Expose
    private List<String> crossReferenceMarkers = null;
    @SerializedName("regions")
    @Expose
    private List<String> regions = null;
    @SerializedName("notes")
    @Expose
    private List<Note> notes = null;

    public List<String> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<String> definitions) {
        this.definitions = definitions;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getShortDefinitions() {
        return shortDefinitions;
    }

    public void setShortDefinitions(List<String> shortDefinitions) {
        this.shortDefinitions = shortDefinitions;
    }

    public List<Subsense> getSubsenses() {
        return subsenses;
    }

    public void setSubsenses(List<Subsense> subsenses) {
        this.subsenses = subsenses;
    }

    public List<ThesaurusLink_> getThesaurusLinks() {
        return thesaurusLinks;
    }

    public void setThesaurusLinks(List<ThesaurusLink_> thesaurusLinks) {
        this.thesaurusLinks = thesaurusLinks;
    }

    public List<CrossReference> getCrossReferences() {
        return crossReferences;
    }

    public void setCrossReferences(List<CrossReference> crossReferences) {
        this.crossReferences = crossReferences;
    }

    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public List<String> getRegisters() {
        return registers;
    }

    public void setRegisters(List<String> registers) {
        this.registers = registers;
    }

    public List<VariantForm> getVariantForms() {
        return variantForms;
    }

    public void setVariantForms(List<VariantForm> variantForms) {
        this.variantForms = variantForms;
    }

    public List<String> getCrossReferenceMarkers() {
        return crossReferenceMarkers;
    }

    public void setCrossReferenceMarkers(List<String> crossReferenceMarkers) {
        this.crossReferenceMarkers = crossReferenceMarkers;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

}

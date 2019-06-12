package com.eagle.pnl.model;


import com.eagle.pnl.constants.EagleContractConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown=true)
public class AccountInstrument {

    @JsonProperty(EagleContractConstants.INSTRUMENT_NAME)
    private String name;

    @JsonProperty(EagleContractConstants.INSTRUMENT_LEVERAGE_FACTOR)
    private Integer leverageFactor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLeverageFactor() {
        return leverageFactor;
    }

    public void setLeverageFactor(Integer leverageFactor) {
        this.leverageFactor = leverageFactor;
    }
}

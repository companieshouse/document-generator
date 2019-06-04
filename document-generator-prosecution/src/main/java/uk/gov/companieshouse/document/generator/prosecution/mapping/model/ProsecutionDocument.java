package uk.gov.companieshouse.document.generator.prosecution.mapping.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant.Defendant;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.offence.Offence;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase.ProsecutionCase;

public abstract class ProsecutionDocument {
	
	@JsonProperty("defendant")
	private Defendant defendant;
	
	@JsonProperty("offence")
	private List<Offence> offences;
	
	@JsonProperty("case")
	private ProsecutionCase prosecutionCase;

	public Defendant getDefendant() {
		return defendant;
	}

	public void setDefendant(Defendant defendant) {
		this.defendant = defendant;
	}

	public List<Offence> getOffences() {
		return offences;
	}

	public void setOffences(List<Offence> offences) {
		this.offences = offences;
	}

	public ProsecutionCase getProsecutionCase() {
		return prosecutionCase;
	}

	public void setProsecutionCase(ProsecutionCase prosecutionCase) {
		this.prosecutionCase = prosecutionCase;
	}
}

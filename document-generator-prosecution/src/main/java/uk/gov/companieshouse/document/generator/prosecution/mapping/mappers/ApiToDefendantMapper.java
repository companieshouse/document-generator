package uk.gov.companieshouse.document.generator.prosecution.mapping.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.prosecution.defendant.AddressApi;
import uk.gov.companieshouse.api.model.prosecution.defendant.DefendantApi;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant.Defendant;

@Component
public class ApiToDefendantMapper {
	
    public Defendant apiToDefendant(DefendantApi defendant) {
    	Defendant def = new Defendant();
    	if(defendant.getIsCorporateAppointment()) {
    		def.setName(defendant.getCompanyOfficerDetailsApi().getCompanyName());
    	}else {
    		StringBuilder name = new StringBuilder();
    		if( checkNotNullOrEmpty(defendant.getPersonOfficerDetailsApi().getTitle())) {
    			name.append(defendant.getPersonOfficerDetailsApi().getTitle() + " ");
    		}
    		if( checkNotNullOrEmpty(defendant.getPersonOfficerDetailsApi().getForename())) {
    			name.append(defendant.getPersonOfficerDetailsApi().getForename() + " ");
    		}
    		if( checkNotNullOrEmpty(defendant.getPersonOfficerDetailsApi().getSurname())) {
    			name.append(defendant.getPersonOfficerDetailsApi().getSurname());
    		}
    		def.setName(name.toString());
    	}
    	def.setAddress(getAddress(defendant));
    	def.setLinks(defendant.getLinks());
    	return def;
    }
    
    private List<String> getAddress(DefendantApi defendant) {
    	List<String> address = new ArrayList<String>();
    	AddressApi addressApi = defendant.getAddressApi();
    	StringBuilder line = new StringBuilder();
    	if(checkNotNullOrEmpty(addressApi.getHouseNameNumber())) {
    		line.append(addressApi.getHouseNameNumber() + " ");
    	}
    	if(checkNotNullOrEmpty(addressApi.getStreet())) {
    		line.append(addressApi.getStreet());
    	}
    	address.add(line.toString());
    	if(checkNotNullOrEmpty(addressApi.getArea())) {
    		address.add(addressApi.getArea());
    	}
    	if(checkNotNullOrEmpty(addressApi.getPostTown())) {
    		address.add(addressApi.getPostTown());
    	}
    	if(checkNotNullOrEmpty(addressApi.getRegion())) {
    		address.add(addressApi.getRegion());
    	}
    	if(checkNotNullOrEmpty(addressApi.getPostCode())) {
    		address.add(addressApi.getPostCode());
    	}
    
    	return address;
    }
    
    private boolean checkNotNullOrEmpty(String s) {
    	if(s != null && s.trim().length() >0 ) {
    		return true;
    	}
    	return false;
    }
}


package com.intuit.craft.dto.request;

import lombok.Data;

@Data
public class ProfileRequest {
    private String profileId;
    private String companyName;
    private String legalName;
    private Address businessAddress;
    private Address legalAddress;
    private TaxDetails taxIdentifier;
    private String email;
    private String website;

    @Data
    public static class Address {
        private String line1;
        private String line2;
        private String city;
        private String state;
        private String zip;
        private String country;
    }

    @Data
    public static class TaxDetails {
        String identifier;
        TaxType type;

        public enum TaxType {
            PAN, EIN
        }
    }
}

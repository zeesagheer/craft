package com.intuit.craft.dto.request;

import lombok.Data;

/**
 * The type Profile request.
 */
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

    /**
     * The type Address.
     */
    @Data
    public static class Address {
        private String line1;
        private String line2;
        private String city;
        private String state;
        private String zip;
        private String country;
    }

    /**
     * The type Tax details.
     */
    @Data
    public static class TaxDetails {
        /**
         * The Identifier.
         */
        String identifier;
        /**
         * The Type.
         */
        TaxType type;

        /**
         * The enum Tax type.
         */
        public enum TaxType {
            /**
             * Pan tax type.
             */
            PAN,
            /**
             * Ein tax type.
             */
            EIN
        }
    }
}

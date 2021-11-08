package com.scott.veille2.model;

public class OfferAppDTO {
    private long idOffer;
    private long idStudent;

    public OfferAppDTO (long idOffer, long idStudent) {
        this.idOffer = idOffer;
        this.idStudent = idStudent;
    }
}

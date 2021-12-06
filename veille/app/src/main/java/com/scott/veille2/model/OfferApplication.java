package com.scott.veille2.model;

public class OfferApplication {
    private Offer offer;
    private String studentName;
    private String status;

    public OfferApplication(Offer offer, String studentName, String status) {
        this.offer = offer;
        this.studentName = studentName;
        this.status = status;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

//package com.petmatz.domain.pet;
//
//public enum PetGender {
//    MALE("수컷"),
//    FEMALE("암컷");
//
//    private final String koreanValue;
//
//    PetGender(String koreanValue) {
//        this.koreanValue = koreanValue;
//    }
//
//    public static PetGender fromString(String gender) {
//        return switch (gender) {
//            case "수컷" -> MALE;
//            case "암컷" -> FEMALE;
//            default -> throw new IllegalArgumentException("Invalid gender: " + gender);
//        };
//    }
//
//    @Override
//    public String toString() {
//        return name();
//    }
//}

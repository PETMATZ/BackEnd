package com.petmatz.domain.old.petmission.exception;

import com.petmatz.common.exception.DomainException;

public class  ExistPetMissionAnswerException extends DomainException {
    public static final DomainException EXCEPTION = new ExistPetMissionAnswerException();

    public ExistPetMissionAnswerException() {
        super(PetMissionErrorCode.DUPLIACTION_PET_MISSION_ANSWER);
    }
}

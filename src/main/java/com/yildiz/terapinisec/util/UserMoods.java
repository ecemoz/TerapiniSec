package com.yildiz.terapinisec.util;

import java.util.EnumSet;
import java.util.Set;

public enum UserMoods {
        // Pozitif Ruh Halleri
        HAPPY, EXCITED, GRATEFUL, RELAXED, ENERGIZED, PROUD, CONFIDENT, LOVED, PEACEFUL, OPTIMISTIC,

        // Nötr Ruh Halleri
        NEUTRAL, BORED, CALM, CONTENT, INDIFFERENT,

        // Negatif Ruh Halleri
        SAD, STRESSED, ANXIOUS, ANGRY, FRUSTRATED, LONELY, TIRED, GUILTY, OVERWHELMED, HOPELESS,

        // Enerji Seviyeleri ile İlgili Mood'lar
        EXHAUSTED, LAZY, MOTIVATED, FOCUSED, DISTRACTED,

        // Sosyal Duygular
        CONNECTED, IGNORED, APPRECIATED, EXCLUDED,

        // Durumsal Ruh Halleri
        NOSTALGIC, CURIOUS, INSECURE, ASHAMED, ADVENTUROUS, PLAYFUL;


        private static final Set<UserMoods> POSITIVE_MOODS = EnumSet.of(
                HAPPY, EXCITED, GRATEFUL, RELAXED, ENERGIZED, PROUD, CONFIDENT, LOVED, PEACEFUL, OPTIMISTIC
        );

        private static final Set<UserMoods> NEGATIVE_MOODS = EnumSet.of(
                SAD, STRESSED, ANXIOUS, ANGRY, FRUSTRATED, LONELY, TIRED, GUILTY, OVERWHELMED, HOPELESS
        );

        public boolean isPositive() {
                return POSITIVE_MOODS.contains(this);
        }

        public boolean isNegative() {
                return NEGATIVE_MOODS.contains(this);
        }
}

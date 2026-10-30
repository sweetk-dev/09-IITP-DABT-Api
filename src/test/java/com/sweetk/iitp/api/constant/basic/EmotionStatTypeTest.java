package com.sweetk.iitp.api.constant.basic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class EmotionStatTypeTest {

    private static final Pattern SAFE_TABLE = Pattern.compile("^stats_dis_emo_[a-z_]{1,40}$");

    @Test
    void allStatsWithUniqueKeysAndSafeTableNames() {
        assertEquals(24, EmotionStatType.values().length);
        assertEquals(24, new HashSet<>(Arrays.stream(EmotionStatType.values()).map(EmotionStatType::getCode).toList()).size());
        assertEquals(24, new HashSet<>(Arrays.stream(EmotionStatType.values()).map(EmotionStatType::getIntgTblId).toList()).size());
        for (EmotionStatType t : EmotionStatType.values()) {
            assertTrue(SAFE_TABLE.matcher(t.getIntgTblId()).matches(), t.getIntgTblId());
        }
    }

    @Test
    void fromStringAcceptsCodeOrNameAndRejectsOthers() {
        assertEquals(EmotionStatType.DEPRESSION, EmotionStatType.fromString("depression"));
        assertEquals(EmotionStatType.SELF_ESTEEM, EmotionStatType.fromString("SELF_ESTEEM"));
        assertNull(EmotionStatType.fromString(" "));
        assertThrows(IllegalArgumentException.class, () -> EmotionStatType.fromString("stats_dis_emo_depression; drop"));
    }
}

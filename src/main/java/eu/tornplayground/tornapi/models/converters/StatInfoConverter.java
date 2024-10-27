package eu.tornplayground.tornapi.models.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import eu.tornplayground.tornapi.models.user.battlestats.StatInfoType;
import eu.tornplayground.tornapi.models.user.battlestats.statinfo.BattleStat;
import eu.tornplayground.tornapi.models.user.battlestats.statinfo.StatInfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class StatInfoConverter extends StdConverter<String, StatInfo> {


    @Override
    public StatInfo convert(String value) {
        String[] percentageSplit = value.split(" to ");
        String[] statSplit = percentageSplit[1].split(" from ");

        final String message = value;
        final String percentage = percentageSplit[0].replace("%", "");
        final String type = statSplit[0];
        final String stat = statSplit[1];

        return StatInfo.builder()
                .message(message)
                .percentage(Short.parseShort(percentage))
                .type(StatInfoType.fromName(type))
                .stat(BattleStat.fromName(stat))
                .build();
    }
}

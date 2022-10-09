package eu.tornplayground.tornapi.mappers;

import eu.tornplayground.tornapi.models.companies.Company;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Collection;
import java.util.Map;

public class CompanyMapper extends ModelMapper {

    private CompanyMapper() {
    }

    public static Collection<Company> ofCompany(JsonNode json) {
        return OBJECT_MAPPER.convertValue(json.get("company"), new TypeReference<Map<Long, Company>>() {
        }).values();
    }

}

package be.dekleinekobini.tornapi.mappers;

import be.dekleinekobini.tornapi.models.user.*;
import be.dekleinekobini.tornapi.models.user.partial.Gender;
import be.dekleinekobini.tornapi.models.user.partial.LastActionStatus;
import be.dekleinekobini.tornapi.models.user.partial.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;

class UserMapperTest {

    @Test
    void ofAmmo() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"ammo\":[{\"ammoID\":2,\"typeID\":1,\"size\":\"9mm Parabellum Round\",\"type\":\"Standard\",\"quantity\":3023,\"equipped\":1},{\"ammoID\":1,\"typeID\":1,\"size\":\"12 Gauge Cartridge\",\"type\":\"Standard\",\"quantity\":2355,\"equipped\":1},{\"ammoID\":1,\"typeID\":5,\"size\":\"12 Gauge Cartridge\",\"type\":\"Incendiary\",\"quantity\":243,\"equipped\":0},{\"ammoID\":3,\"typeID\":1,\"size\":\"5.7mm High Vel. Round\",\"type\":\"Standard\",\"quantity\":390,\"equipped\":0},{\"ammoID\":4,\"typeID\":1,\"size\":\"7.62 mm Rifle Round\",\"type\":\"Standard\",\"quantity\":3797,\"equipped\":0},{\"ammoID\":4,\"typeID\":2,\"size\":\"7.62 mm Rifle Round\",\"type\":\"Hollow Point\",\"quantity\":2146,\"equipped\":0},{\"ammoID\":4,\"typeID\":3,\"size\":\"7.62 mm Rifle Round\",\"type\":\"Piercing\",\"quantity\":2000,\"equipped\":0},{\"ammoID\":4,\"typeID\":4,\"size\":\"7.62 mm Rifle Round\",\"type\":\"Tracer\",\"quantity\":2500,\"equipped\":0},{\"ammoID\":4,\"typeID\":5,\"size\":\"7.62 mm Rifle Round\",\"type\":\"Incendiary\",\"quantity\":1500,\"equipped\":0},{\"ammoID\":5,\"typeID\":1,\"size\":\"5.56mm Rifle Round\",\"type\":\"Standard\",\"quantity\":88568,\"equipped\":0},{\"ammoID\":5,\"typeID\":2,\"size\":\"5.56mm Rifle Round\",\"type\":\"Hollow Point\",\"quantity\":274,\"equipped\":0},{\"ammoID\":5,\"typeID\":3,\"size\":\"5.56mm Rifle Round\",\"type\":\"Piercing\",\"quantity\":1500,\"equipped\":0},{\"ammoID\":5,\"typeID\":4,\"size\":\"5.56mm Rifle Round\",\"type\":\"Tracer\",\"quantity\":500,\"equipped\":0},{\"ammoID\":5,\"typeID\":5,\"size\":\"5.56mm Rifle Round\",\"type\":\"Incendiary\",\"quantity\":500,\"equipped\":0},{\"ammoID\":9,\"typeID\":1,\"size\":\"5.45mm Rifle Round\",\"type\":\"Standard\",\"quantity\":90,\"equipped\":0},{\"ammoID\":10,\"typeID\":1,\"size\":\".45 ACP Round\",\"type\":\"Standard\",\"quantity\":966,\"equipped\":0},{\"ammoID\":17,\"typeID\":1,\"size\":\".380 ACP Round\",\"type\":\"Standard\",\"quantity\":38,\"equipped\":0},{\"ammoID\":18,\"typeID\":1,\"size\":\"Liter of Fuel\",\"type\":\"Standard\",\"quantity\":3186,\"equipped\":0},{\"ammoID\":15,\"typeID\":1,\"size\":\"Flare\",\"type\":\"Standard\",\"quantity\":1000,\"equipped\":0}]}");

        // Act
        List<Ammo> ammo = UserMapper.ofAmmo(json);

        // Assert
        Ammo standard9mm = new Ammo();
        standard9mm.setAmmoId(2);
        standard9mm.setTypeId(1);
        standard9mm.setSize("9mm Parabellum Round");
        standard9mm.setType("Standard");
        standard9mm.setEquipped(true);

        Ammo hollow556 = new Ammo();
        hollow556.setAmmoId(5);
        hollow556.setTypeId(2);
        hollow556.setSize("5.56mm Rifle Round");
        hollow556.setType("Hollow Point");
        hollow556.setEquipped(false);

        assertThat(ammo)
                .hasSize(19)
                .contains(standard9mm, hollow556);
    }

    @Test
    void ofAttacksFull() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"attacks\":{\"163385223\":{\"code\":\"539b48279f80d57eece5971054962911\",\"timestamp_started\":1631438210,\"timestamp_ended\":1631438215,\"attacker_id\":2114440,\"attacker_faction\":20747,\"defender_id\":2084369,\"defender_faction\":17001,\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.39},\"163385401\":{\"code\":\"706a224293d3bb7d50c563c16f0719c2\",\"timestamp_started\":1631438292,\"timestamp_ended\":1631438295,\"attacker_id\":2114440,\"attacker_faction\":20747,\"defender_id\":2676799,\"defender_faction\":14686,\"result\":\"Attacked\",\"stealthed\":0,\"respect\":1.18},\"163385417\":{\"code\":\"9bf45a9c984947a852e985d619840868\",\"timestamp_started\":1631438299,\"timestamp_ended\":1631438301,\"attacker_id\":2114440,\"attacker_faction\":20747,\"defender_id\":2546876,\"defender_faction\":40039,\"result\":\"Attacked\",\"stealthed\":0,\"respect\":1.36}}}\n");

        // Act
        Map<Long, Attacks> result = UserMapper.ofAttacks(json);

        // Assert
        Attacks attack1 = new Attacks();
        attack1.setCode("539b48279f80d57eece5971054962911");
        attack1.setTimestampStarted(LocalDateTime.of(2021, 9, 12, 9, 16, 50));
        attack1.setTimestampEnded(LocalDateTime.of(2021, 9, 12, 9, 16, 55));
        attack1.setAttackerId(2114440);
        attack1.setAttackerFaction(20747);
        attack1.setDefenderId(2084369);
        attack1.setDefenderFaction(17001);
        attack1.setResult(Attacks.AttackResult.ATTACKED);
        attack1.setStealthed(false);
        attack1.setRespect(3.39);

        assertThat(result)
                .hasSize(3)
                .containsEntry(163385223L, attack1);
    }

    @Test
    void ofAttacks() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"attacks\":{\"173744624\":{\"code\":\"51d4d4db0d303be93b6fe3aa3cb28205\",\"timestamp_started\":1636972534,\"timestamp_ended\":1636972544,\"attacker_id\":1151690,\"attacker_name\":\"sharkeyfive\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":0,\"respect\":0,\"chain\":34,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.4,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.13}},\"173762110\":{\"code\":\"88be3b0f34c7bfb517bf2faa7ec458dd\",\"timestamp_started\":1636987625,\"timestamp_ended\":1636987636,\"attacker_id\":1456295,\"attacker_name\":\"Nepy\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":0,\"respect\":0,\"chain\":664,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.47,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.46}},\"173766799\":{\"code\":\"e049a2370da258c8b6bac54fd958acc3\",\"timestamp_started\":1636991281,\"timestamp_ended\":1636991295,\"attacker_id\":\"\",\"attacker_name\":\"\",\"attacker_faction\":\"\",\"attacker_factionname\":\"\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":1,\"respect\":0,\"chain\":773,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.4,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.47}},\"173766871\":{\"code\":\"caae395d1dc40ddcc951547d2c79bfb2\",\"timestamp_started\":1636991344,\"timestamp_ended\":1636991352,\"attacker_id\":1602158,\"attacker_name\":\"Queequeg\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Assist\",\"stealthed\":0,\"respect\":0,\"chain\":0,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173766947\":{\"code\":\"c620566455566d5c8ed0d23edf6fe271\",\"timestamp_started\":1636991395,\"timestamp_ended\":1636991402,\"attacker_id\":1335768,\"attacker_name\":\"nerbas\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":0,\"respect\":0,\"chain\":779,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.4,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.47}},\"173767020\":{\"code\":\"bddc7ba29c3fcb76b416bb50c560df0d\",\"timestamp_started\":1636991456,\"timestamp_ended\":1636991462,\"attacker_id\":1335768,\"attacker_name\":\"nerbas\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":0,\"respect\":0,\"chain\":782,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.4,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.47}},\"173767280\":{\"code\":\"0ab08b1e5aea9fb8236db72dca55b955\",\"timestamp_started\":1636991684,\"timestamp_ended\":1636991754,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1673331,\"defender_name\":\"Marshmalllow\",\"defender_faction\":2013,\"defender_factionname\":\"Subversive  Alliance\",\"result\":\"Hospitalized\",\"stealthed\":0,\"respect\":10.5,\"chain\":2,\"raid\":1,\"respect_gain\":0,\"respect_loss\":10.5,\"modifiers\":{\"fair_fight\":3,\"war\":2,\"retaliation\":1,\"group_attack\":1.25,\"overseas\":1,\"chain_bonus\":1}},\"173767394\":{\"code\":\"edf48b30dd2d920a58cd54374ca2aeb5\",\"timestamp_started\":1636991779,\"timestamp_ended\":1636991783,\"attacker_id\":\"\",\"attacker_name\":\"\",\"attacker_faction\":\"\",\"attacker_factionname\":\"\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":1,\"respect\":0,\"chain\":796,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.4,\"war\":2,\"retaliation\":1.5,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.48}},\"173767575\":{\"code\":\"f20bf11d782d6fc089f52757bf9f83e1\",\"timestamp_started\":1636991958,\"timestamp_ended\":1636991972,\"attacker_id\":1161983,\"attacker_name\":\"TwoEleven\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":0,\"respect\":0,\"chain\":804,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.4,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.48}},\"173767696\":{\"code\":\"9db85495fb860c1c26051759c95ff4c4\",\"timestamp_started\":1636992074,\"timestamp_ended\":1636992094,\"attacker_id\":220641,\"attacker_name\":\"Twigman\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":0,\"respect\":0,\"chain\":809,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.4,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.48}},\"173767789\":{\"code\":\"7ebbdc9851adf42790504b017e625f16\",\"timestamp_started\":1636992161,\"timestamp_ended\":1636992171,\"attacker_id\":220641,\"attacker_name\":\"Twigman\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":0,\"respect\":0,\"chain\":812,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.4,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.48}},\"173768004\":{\"code\":\"37fbd5aedb8600f7adf23a3310e90a22\",\"timestamp_started\":1636992354,\"timestamp_ended\":1636992360,\"attacker_id\":\"\",\"attacker_name\":\"\",\"attacker_faction\":\"\",\"attacker_factionname\":\"\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":1,\"respect\":0,\"chain\":819,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.4,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.48}},\"173791827\":{\"code\":\"2d764857d26c1be8f7d1d2b41e5da5c2\",\"timestamp_started\":1637010864,\"timestamp_ended\":1637010873,\"attacker_id\":1689701,\"attacker_name\":\"miro\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":0,\"chain\":33,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.4,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.13}},\"173852617\":{\"code\":\"7d48b56f18834cfff0e13be36bdf2967\",\"timestamp_started\":1637076220,\"timestamp_ended\":1637076240,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1161983,\"defender_name\":\"TwoEleven\",\"defender_faction\":2013,\"defender_factionname\":\"Subversive  Alliance\",\"result\":\"Assist\",\"stealthed\":0,\"respect\":0,\"chain\":0,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173852745\":{\"code\":\"a6bf07ddf12255c6555a6d54600174ec\",\"timestamp_started\":1637076375,\"timestamp_ended\":1637076383,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":206761,\"defender_name\":\"Dr_Who\",\"defender_faction\":2013,\"defender_factionname\":\"Subversive  Alliance\",\"result\":\"Assist\",\"stealthed\":0,\"respect\":0,\"chain\":0,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173855393\":{\"code\":\"523095162bd33b5dc01ee91372a97713\",\"timestamp_started\":1637079254,\"timestamp_ended\":1637079261,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":420899,\"defender_name\":\"BLM\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Lost\",\"stealthed\":0,\"respect\":0,\"chain\":0,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173855405\":{\"code\":\"c59b8de325d4534d1fe976b0ab39dca5\",\"timestamp_started\":1637079272,\"timestamp_ended\":1637079277,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":420899,\"defender_name\":\"BLM\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Special\",\"stealthed\":0,\"respect\":4.11,\"chain\":1,\"raid\":0,\"respect_gain\":4.11,\"respect_loss\":1.03,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173856428\":{\"code\":\"faa7feba8402198bc7624abb4e4dcb31\",\"timestamp_started\":1637080499,\"timestamp_ended\":1637080505,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1582406,\"defender_name\":\"Midnight_Bomber\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.69,\"chain\":1,\"raid\":0,\"respect_gain\":3.69,\"respect_loss\":0.92,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173866625\":{\"code\":\"466de201fa5da38460de791d7529976c\",\"timestamp_started\":1637091120,\"timestamp_ended\":1637091131,\"attacker_id\":667086,\"attacker_name\":\"ripp3r\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":0,\"respect\":0,\"chain\":6,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.4,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173866736\":{\"code\":\"60a1595e41d91ba561f5dcb9354f4bc5\",\"timestamp_started\":1637091225,\"timestamp_ended\":1637091233,\"attacker_id\":667086,\"attacker_name\":\"ripp3r\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":0,\"chain\":0,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173871440\":{\"code\":\"3875de3bc45d39075e77dbc6e53bf631\",\"timestamp_started\":1637096401,\"timestamp_ended\":1637096408,\"attacker_id\":719629,\"attacker_name\":\"ThaBishop\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":0,\"respect\":0,\"chain\":7,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.4,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173880221\":{\"code\":\"a1dd71289b19818ace9a1baa0cf2c285\",\"timestamp_started\":1637106245,\"timestamp_ended\":1637106270,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1047758,\"defender_name\":\"Jozo-ZC\",\"defender_faction\":10818,\"defender_factionname\":\"Lex Wolverines\",\"result\":\"Mugged\",\"stealthed\":0,\"respect\":3.08,\"chain\":1,\"raid\":0,\"respect_gain\":3.08,\"respect_loss\":0.77,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173880245\":{\"code\":\"eea00f6b0b240cb4330410b6afe5c196\",\"timestamp_started\":1637106281,\"timestamp_ended\":1637106303,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1132931,\"defender_name\":\"TeaLeaf\",\"defender_faction\":9710,\"defender_factionname\":\"Banzai Bandits\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.37,\"chain\":2,\"raid\":0,\"respect_gain\":3.37,\"respect_loss\":0.84,\"modifiers\":{\"fair_fight\":2.55,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173910537\":{\"code\":\"6c52ac25ca389976524167a6e15b4197\",\"timestamp_started\":1637142036,\"timestamp_ended\":1637142056,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1132931,\"defender_name\":\"TeaLeaf\",\"defender_faction\":9710,\"defender_factionname\":\"Banzai Bandits\",\"result\":\"Mugged\",\"stealthed\":0,\"respect\":2.52,\"chain\":2,\"raid\":0,\"respect_gain\":2.52,\"respect_loss\":0.63,\"modifiers\":{\"fair_fight\":2.55,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173910555\":{\"code\":\"00f3cb7409b8694918b7947a55d2f1d5\",\"timestamp_started\":1637142062,\"timestamp_ended\":1637142077,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1047758,\"defender_name\":\"Jozo-ZC\",\"defender_faction\":10818,\"defender_factionname\":\"Lex Wolverines\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":4.11,\"chain\":3,\"raid\":0,\"respect_gain\":4.11,\"respect_loss\":1.03,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173913728\":{\"code\":\"fb77661e097494a9d782f0afc2310243\",\"timestamp_started\":1637145689,\"timestamp_ended\":1637145706,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1047758,\"defender_name\":\"Jozo-ZC\",\"defender_faction\":10818,\"defender_factionname\":\"Lex Wolverines\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":4.11,\"chain\":2,\"raid\":0,\"respect_gain\":4.11,\"respect_loss\":1.03,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173913753\":{\"code\":\"04212b58cd6ca46df5b422d97034f0f5\",\"timestamp_started\":1637145711,\"timestamp_ended\":1637145718,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1132931,\"defender_name\":\"TeaLeaf\",\"defender_faction\":9710,\"defender_factionname\":\"Banzai Bandits\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.37,\"chain\":3,\"raid\":0,\"respect_gain\":3.37,\"respect_loss\":0.84,\"modifiers\":{\"fair_fight\":2.55,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173914047\":{\"code\":\"843bef9e69df9b8ab51c0d10a3895c48\",\"timestamp_started\":1637145978,\"timestamp_ended\":1637145986,\"attacker_id\":719629,\"attacker_name\":\"ThaBishop\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Mugged\",\"stealthed\":0,\"respect\":0,\"chain\":10,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":10}},\"173919688\":{\"code\":\"5ad399799e09be986a09454b2abbd815\",\"timestamp_started\":1637152161,\"timestamp_ended\":1637152178,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1132931,\"defender_name\":\"TeaLeaf\",\"defender_faction\":9710,\"defender_factionname\":\"Banzai Bandits\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.37,\"chain\":1,\"raid\":0,\"respect_gain\":3.37,\"respect_loss\":0.84,\"modifiers\":{\"fair_fight\":2.55,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173919710\":{\"code\":\"c190cb174b8478fef45d4d092e4df456\",\"timestamp_started\":1637152184,\"timestamp_ended\":1637152201,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1047758,\"defender_name\":\"Jozo-ZC\",\"defender_faction\":10818,\"defender_factionname\":\"Lex Wolverines\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":4.11,\"chain\":2,\"raid\":0,\"respect_gain\":4.11,\"respect_loss\":1.03,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173922368\":{\"code\":\"771d2fb6551531de4e4bfc24e2473e30\",\"timestamp_started\":1637154778,\"timestamp_ended\":1637154786,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1132931,\"defender_name\":\"TeaLeaf\",\"defender_faction\":9710,\"defender_factionname\":\"Banzai Bandits\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.37,\"chain\":1,\"raid\":0,\"respect_gain\":3.37,\"respect_loss\":0.84,\"modifiers\":{\"fair_fight\":2.55,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173922379\":{\"code\":\"00d5f3da71c99e944c86ec3d2ae24f17\",\"timestamp_started\":1637154790,\"timestamp_ended\":1637154800,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1047758,\"defender_name\":\"Jozo-ZC\",\"defender_faction\":10818,\"defender_factionname\":\"Lex Wolverines\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":4.11,\"chain\":2,\"raid\":0,\"respect_gain\":4.11,\"respect_loss\":1.03,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173922516\":{\"code\":\"817dbb909b4945d76d222818c6be901d\",\"timestamp_started\":1637154914,\"timestamp_ended\":1637154931,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1791295,\"defender_name\":\"Nive\",\"defender_faction\":89,\"defender_factionname\":\"Catalysis\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.99,\"chain\":4,\"raid\":0,\"respect_gain\":3.99,\"respect_loss\":1,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173922765\":{\"code\":\"24777a52bdfcb8d076f4f6b17ed1efca\",\"timestamp_started\":1637155119,\"timestamp_ended\":1637155127,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":2143655,\"defender_name\":\"3dubbeltjes\",\"defender_faction\":8836,\"defender_factionname\":\"Vinerri\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":4.05,\"chain\":1,\"raid\":0,\"respect_gain\":4.05,\"respect_loss\":1.01,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173922797\":{\"code\":\"fdd5f183bd3b4c04812b74f87a847e45\",\"timestamp_started\":1637155159,\"timestamp_ended\":1637155169,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1988849,\"defender_name\":\"TopengPerak\",\"defender_faction\":13343,\"defender_factionname\":\"The Defiant\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.9,\"chain\":2,\"raid\":0,\"respect_gain\":3.9,\"respect_loss\":0.98,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173922861\":{\"code\":\"65e5fc76dee0c2ea175438161d3ddbe3\",\"timestamp_started\":1637155216,\"timestamp_ended\":1637155228,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":238789,\"defender_name\":\"Aatif\",\"defender_faction\":8802,\"defender_factionname\":\"Bengal Tigers\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":4.02,\"chain\":3,\"raid\":0,\"respect_gain\":4.02,\"respect_loss\":1.01,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173922952\":{\"code\":\"98ffba9683a39086348a5c09b1056794\",\"timestamp_started\":1637155300,\"timestamp_ended\":1637155317,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1883498,\"defender_name\":\"RandomSaiyan\",\"defender_faction\":35965,\"defender_factionname\":\"The Saviors\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.99,\"chain\":4,\"raid\":0,\"respect_gain\":3.99,\"respect_loss\":1,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173922979\":{\"code\":\"bb529c17a8c5c9239fe9a80430782485\",\"timestamp_started\":1637155337,\"timestamp_ended\":1637155345,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":2514472,\"defender_name\":\"BRYAN\",\"defender_faction\":89,\"defender_factionname\":\"Catalysis\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.98,\"chain\":5,\"raid\":0,\"respect_gain\":3.98,\"respect_loss\":1,\"modifiers\":{\"fair_fight\":2.95,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173923001\":{\"code\":\"a2a2a7535252761592c34b03d337a4b4\",\"timestamp_started\":1637155355,\"timestamp_ended\":1637155385,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1672553,\"defender_name\":\"Avigdor\",\"defender_faction\":937,\"defender_factionname\":\"Sport Club\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":4.02,\"chain\":6,\"raid\":0,\"respect_gain\":4.02,\"respect_loss\":1.01,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173923036\":{\"code\":\"42f86fe74513d27da2ac4651001be355\",\"timestamp_started\":1637155407,\"timestamp_ended\":1637155411,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1403598,\"defender_name\":\"Shakey\",\"defender_faction\":32585,\"defender_factionname\":\"Heart of a Pirate\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.35,\"chain\":7,\"raid\":0,\"respect_gain\":3.35,\"respect_loss\":0.84,\"modifiers\":{\"fair_fight\":2.77,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173923057\":{\"code\":\"b919ac424208a527c1d5cc6ffef1b6e9\",\"timestamp_started\":1637155428,\"timestamp_ended\":1637155441,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":2190417,\"defender_name\":\"JuneBoozy\",\"defender_faction\":89,\"defender_factionname\":\"Catalysis\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.89,\"chain\":1,\"raid\":0,\"respect_gain\":3.89,\"respect_loss\":0.97,\"modifiers\":{\"fair_fight\":2.88,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173923123\":{\"code\":\"814f54c9030527058d90ae22183d28c1\",\"timestamp_started\":1637155485,\"timestamp_ended\":1637155498,\"attacker_id\":111337,\"attacker_name\":\"PxKSnowball\",\"attacker_faction\":89,\"attacker_factionname\":\"Catalysis\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":0,\"respect\":0,\"chain\":1,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.87,\"war\":1,\"retaliation\":1.5,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173923217\":{\"code\":\"487eb7a8e4eb4bffebdf4d3292adde85\",\"timestamp_started\":1637155590,\"timestamp_ended\":1637155600,\"attacker_id\":111337,\"attacker_name\":\"PxKSnowball\",\"attacker_faction\":89,\"attacker_factionname\":\"Catalysis\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":0,\"respect\":0,\"chain\":2,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.87,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173927595\":{\"code\":\"d414a520b2b5dcb0b4c102902c046b1f\",\"timestamp_started\":1637159936,\"timestamp_ended\":1637159955,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1047758,\"defender_name\":\"Jozo-ZC\",\"defender_faction\":10818,\"defender_factionname\":\"Lex Wolverines\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":4.11,\"chain\":1,\"raid\":0,\"respect_gain\":4.11,\"respect_loss\":1.03,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173927617\":{\"code\":\"296da1e0456d57cf872407dd8b3f413e\",\"timestamp_started\":1637159960,\"timestamp_ended\":1637159971,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1132931,\"defender_name\":\"TeaLeaf\",\"defender_faction\":9710,\"defender_factionname\":\"Banzai Bandits\",\"result\":\"Attacked\",\"stealthed\":1,\"respect\":3.37,\"chain\":2,\"raid\":0,\"respect_gain\":3.37,\"respect_loss\":0.84,\"modifiers\":{\"fair_fight\":2.55,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173949330\":{\"code\":\"07e5f62005cd4d0d765802448cf250a9\",\"timestamp_started\":1637177020,\"timestamp_ended\":1637177034,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1132931,\"defender_name\":\"TeaLeaf\",\"defender_faction\":9710,\"defender_factionname\":\"Banzai Bandits\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.37,\"chain\":2,\"raid\":0,\"respect_gain\":3.37,\"respect_loss\":0.84,\"modifiers\":{\"fair_fight\":2.55,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173949363\":{\"code\":\"9bf052d555fa36f14547e89536cb1f49\",\"timestamp_started\":1637177044,\"timestamp_ended\":1637177071,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1047758,\"defender_name\":\"Jozo-ZC\",\"defender_faction\":10818,\"defender_factionname\":\"Lex Wolverines\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":4.11,\"chain\":3,\"raid\":0,\"respect_gain\":4.11,\"respect_loss\":1.03,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173949445\":{\"code\":\"6f4f69e2e4557d80ffe5c0f3e5d8ca7d\",\"timestamp_started\":1637177107,\"timestamp_ended\":1637177111,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1061883,\"defender_name\":\"lordfeezal\",\"defender_faction\":12863,\"defender_factionname\":\"Valhalla - Bound\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.52,\"chain\":4,\"raid\":0,\"respect_gain\":3.52,\"respect_loss\":0.88,\"modifiers\":{\"fair_fight\":2.73,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173949466\":{\"code\":\"8b9901c061b9265793ec4ab9a27c2603\",\"timestamp_started\":1637177123,\"timestamp_ended\":1637177132,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1338725,\"defender_name\":\"Fred_DeWalt\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.69,\"chain\":5,\"raid\":0,\"respect_gain\":3.69,\"respect_loss\":0.92,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173949486\":{\"code\":\"94bd369a2646f8b94fb7cc1256eed16d\",\"timestamp_started\":1637177145,\"timestamp_ended\":1637177148,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1481945,\"defender_name\":\"Megabyte\",\"defender_faction\":11898,\"defender_factionname\":\"BUTTERFLY\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.81,\"chain\":6,\"raid\":0,\"respect_gain\":3.81,\"respect_loss\":0.95,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173949501\":{\"code\":\"2989137be819d5384367e2c6d406a645\",\"timestamp_started\":1637177156,\"timestamp_ended\":1637177165,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1903480,\"defender_name\":\"Harrivchaw\",\"defender_faction\":15446,\"defender_factionname\":\"Dopeheads on Mopeds\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.87,\"chain\":7,\"raid\":0,\"respect_gain\":3.87,\"respect_loss\":0.97,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173949558\":{\"code\":\"94aac4571cd8cf9643fb918285b70d97\",\"timestamp_started\":1637177203,\"timestamp_ended\":1637177209,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":239697,\"defender_name\":\"Stinky_Finger\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.81,\"chain\":8,\"raid\":0,\"respect_gain\":3.81,\"respect_loss\":0.95,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173949571\":{\"code\":\"f5bb3b9042a6958a94a08a8ab2b533b9\",\"timestamp_started\":1637177215,\"timestamp_ended\":1637177222,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":215524,\"defender_name\":\"B1ade\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.81,\"chain\":9,\"raid\":0,\"respect_gain\":3.81,\"respect_loss\":0.95,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173949594\":{\"code\":\"c8be8059149f5d61c2ed970c91680cee\",\"timestamp_started\":1637177238,\"timestamp_ended\":1637177245,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":229072,\"defender_name\":\"Sykor\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.51,\"chain\":1,\"raid\":0,\"respect_gain\":3.51,\"respect_loss\":0.88,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173952212\":{\"code\":\"5dc32765e986fa9aa33d2d69fa66deeb\",\"timestamp_started\":1637179200,\"timestamp_ended\":1637179209,\"attacker_id\":667086,\"attacker_name\":\"ripp3r\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Mugged\",\"stealthed\":0,\"respect\":0,\"chain\":24,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.4,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.1}},\"173962430\":{\"code\":\"260c7fd9061c5a2a96cb6ea9e9c554ee\",\"timestamp_started\":1637186849,\"timestamp_ended\":1637186864,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1132931,\"defender_name\":\"TeaLeaf\",\"defender_faction\":9710,\"defender_factionname\":\"Banzai Bandits\",\"result\":\"Attacked\",\"stealthed\":1,\"respect\":3.37,\"chain\":1,\"raid\":0,\"respect_gain\":3.37,\"respect_loss\":0.84,\"modifiers\":{\"fair_fight\":2.55,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"173962475\":{\"code\":\"30039ab33b07400b5c730da20000fce2\",\"timestamp_started\":1637186889,\"timestamp_ended\":1637186897,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1047758,\"defender_name\":\"Jozo-ZC\",\"defender_faction\":10818,\"defender_factionname\":\"Lex Wolverines\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":4.11,\"chain\":2,\"raid\":0,\"respect_gain\":4.11,\"respect_loss\":1.03,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174006824\":{\"code\":\"2c6a835869687996eb7ffde2395aabf8\",\"timestamp_started\":1637231347,\"timestamp_ended\":1637231356,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1132931,\"defender_name\":\"TeaLeaf\",\"defender_faction\":9710,\"defender_factionname\":\"Banzai Bandits\",\"result\":\"Attacked\",\"stealthed\":1,\"respect\":3.37,\"chain\":1,\"raid\":0,\"respect_gain\":3.37,\"respect_loss\":0.84,\"modifiers\":{\"fair_fight\":2.55,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174006894\":{\"code\":\"9446031dd73125ebb77716793d5864ba\",\"timestamp_started\":1637231453,\"timestamp_ended\":1637231463,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1097729,\"defender_name\":\"-swing-\",\"defender_faction\":13851,\"defender_factionname\":\"Nuclear Engineering\",\"result\":\"Mugged\",\"stealthed\":0,\"respect\":1.12,\"chain\":3,\"raid\":0,\"respect_gain\":1.12,\"respect_loss\":0.28,\"modifiers\":{\"fair_fight\":1.27,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174006992\":{\"code\":\"c9cfc1e57126f11e1f82ba53933e46a0\",\"timestamp_started\":1637231583,\"timestamp_ended\":1637231615,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1047758,\"defender_name\":\"Jozo-ZC\",\"defender_faction\":10818,\"defender_factionname\":\"Lex Wolverines\",\"result\":\"Stalemate\",\"stealthed\":0,\"respect\":0,\"chain\":0,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174031123\":{\"code\":\"47a2c36d91d7bc836ed501523877a092\",\"timestamp_started\":1637253630,\"timestamp_ended\":1637253661,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1132931,\"defender_name\":\"TeaLeaf\",\"defender_faction\":9710,\"defender_factionname\":\"Banzai Bandits\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.37,\"chain\":1,\"raid\":0,\"respect_gain\":3.37,\"respect_loss\":0.84,\"modifiers\":{\"fair_fight\":2.55,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174031173\":{\"code\":\"dfc1b6b7e73a06196cfe4f94d22660aa\",\"timestamp_started\":1637253668,\"timestamp_ended\":1637253697,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1047758,\"defender_name\":\"Jozo-ZC\",\"defender_faction\":10818,\"defender_factionname\":\"Lex Wolverines\",\"result\":\"Stalemate\",\"stealthed\":0,\"respect\":0,\"chain\":0,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174057403\":{\"code\":\"f9d87311681da4c635829871ef214aaa\",\"timestamp_started\":1637279187,\"timestamp_ended\":1637279233,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1132931,\"defender_name\":\"TeaLeaf\",\"defender_faction\":9710,\"defender_factionname\":\"Banzai Bandits\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.35,\"chain\":1,\"raid\":0,\"respect_gain\":3.35,\"respect_loss\":0.84,\"modifiers\":{\"fair_fight\":2.54,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174090437\":{\"code\":\"ef6283f7bc593f1a09840c0d60864028\",\"timestamp_started\":1637316437,\"timestamp_ended\":1637316616,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":4,\"defender_name\":\"Duke\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Stalemate\",\"stealthed\":0,\"respect\":0,\"chain\":0,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174092700\":{\"code\":\"278f08d9dedcc3ff6a2869f492fd0fb7\",\"timestamp_started\":1637319104,\"timestamp_ended\":1637319112,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":2503791,\"defender_name\":\"-Coy-\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Attacked\",\"stealthed\":1,\"respect\":0.25,\"chain\":1,\"raid\":0,\"respect_gain\":0.25,\"respect_loss\":0.06,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174116985\":{\"code\":\"f41f9de95df1ea8907b46ea9be539c2a\",\"timestamp_started\":1637334292,\"timestamp_ended\":1637334432,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":20,\"defender_name\":\"Fernando\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Assist\",\"stealthed\":0,\"respect\":0,\"chain\":0,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174208836\":{\"code\":\"fec569b9098e8ea6d405ac6cab9ac1f8\",\"timestamp_started\":1637370203,\"timestamp_ended\":1637370209,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":133606,\"defender_name\":\"WhiteHart\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":4.11,\"chain\":1,\"raid\":0,\"respect_gain\":4.11,\"respect_loss\":1.03,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174226681\":{\"code\":\"e97e8aff89eb81f87c60f31ac9304971\",\"timestamp_started\":1637378454,\"timestamp_ended\":1637378495,\"attacker_id\":227744,\"attacker_name\":\"RumbLE\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Mugged\",\"stealthed\":0,\"respect\":0,\"chain\":2,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.41,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174239709\":{\"code\":\"f32c4419fb18ade816e60d8261d4bbdb\",\"timestamp_started\":1637385978,\"timestamp_ended\":1637385989,\"attacker_id\":\"\",\"attacker_name\":\"\",\"attacker_faction\":\"\",\"attacker_factionname\":\"\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Mugged\",\"stealthed\":1,\"respect\":0,\"chain\":2,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.41,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174380546\":{\"code\":\"b675f8aad18b8014859605c1adacacfb\",\"timestamp_started\":1637448851,\"timestamp_ended\":1637448859,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":955788,\"defender_name\":\"grayddin\",\"defender_faction\":8285,\"defender_factionname\":\"Get Stuffed\",\"result\":\"Mugged\",\"stealthed\":0,\"respect\":2.13,\"chain\":1,\"raid\":0,\"respect_gain\":2.13,\"respect_loss\":0.53,\"modifiers\":{\"fair_fight\":1.83,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1.25,\"chain_bonus\":1}},\"174380620\":{\"code\":\"f58d71f0485dc45c47c6d033b945d8cf\",\"timestamp_started\":1637448889,\"timestamp_ended\":1637448895,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":2447134,\"defender_name\":\"Souse\",\"defender_faction\":35280,\"defender_factionname\":\"Alcoholic Drug Abusers\",\"result\":\"Mugged\",\"stealthed\":0,\"respect\":1.84,\"chain\":2,\"raid\":0,\"respect_gain\":1.84,\"respect_loss\":0.46,\"modifiers\":{\"fair_fight\":1.52,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1.25,\"chain_bonus\":1}},\"174380667\":{\"code\":\"783bab75f1dab5a7e5bb17364676781e\",\"timestamp_started\":1637448907,\"timestamp_ended\":1637448911,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":282330,\"defender_name\":\"MrFadi\",\"defender_faction\":17845,\"defender_factionname\":\"OS Medical\",\"result\":\"Mugged\",\"stealthed\":0,\"respect\":1.45,\"chain\":3,\"raid\":0,\"respect_gain\":1.45,\"respect_loss\":0.36,\"modifiers\":{\"fair_fight\":1.25,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1.25,\"chain_bonus\":1}},\"174481511\":{\"code\":\"c2b7b8ea535687c437a1a98c5147588a\",\"timestamp_started\":1637511580,\"timestamp_ended\":1637511584,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":2260165,\"defender_name\":\"Mercer\",\"defender_faction\":366,\"defender_factionname\":\"Evolution\",\"result\":\"Mugged\",\"stealthed\":0,\"respect\":3.5,\"chain\":1,\"raid\":0,\"respect_gain\":3.5,\"respect_loss\":0.88,\"modifiers\":{\"fair_fight\":2.83,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1.25,\"chain_bonus\":1}},\"174510260\":{\"code\":\"47246797d049fdbf1fe32066bb0c5ee1\",\"timestamp_started\":1637531328,\"timestamp_ended\":1637531341,\"attacker_id\":\"\",\"attacker_name\":\"\",\"attacker_faction\":\"\",\"attacker_factionname\":\"\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Attacked\",\"stealthed\":1,\"respect\":0,\"chain\":341,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.41,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.38}},\"174512762\":{\"code\":\"5a1a0050283f28bfd844c4e2cb9e388f\",\"timestamp_started\":1637533001,\"timestamp_ended\":1637533006,\"attacker_id\":\"\",\"attacker_name\":\"\",\"attacker_faction\":\"\",\"attacker_factionname\":\"\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Mugged\",\"stealthed\":1,\"respect\":0,\"chain\":415,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.41,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.4}},\"174516701\":{\"code\":\"f0f6eac94d5f2f7e59fdf7cbb24b4b84\",\"timestamp_started\":1637535693,\"timestamp_ended\":1637535703,\"attacker_id\":1456295,\"attacker_name\":\"Nepy\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":0,\"respect\":0,\"chain\":504,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.48,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.43}},\"174517583\":{\"code\":\"9fe87290e589e584e8bec510dca1f4ee\",\"timestamp_started\":1637536296,\"timestamp_ended\":1637536304,\"attacker_id\":234103,\"attacker_name\":\"Morcha\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":0,\"chain\":530,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.41,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.43}},\"174521163\":{\"code\":\"fa6ba5c583030e61fba50900e22fcf77\",\"timestamp_started\":1637538980,\"timestamp_ended\":1637538985,\"attacker_id\":\"\",\"attacker_name\":\"\",\"attacker_faction\":\"\",\"attacker_factionname\":\"\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":1,\"respect\":0,\"chain\":585,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.41,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.44}},\"174555162\":{\"code\":\"f468d76a4feaec9e627ad5028ac968b7\",\"timestamp_started\":1637566520,\"timestamp_ended\":1637566532,\"attacker_id\":\"\",\"attacker_name\":\"\",\"attacker_faction\":\"\",\"attacker_factionname\":\"\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":1,\"respect\":0,\"chain\":5,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.41,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174561868\":{\"code\":\"d918dd8d4c65bf5f59da87ce89ad5882\",\"timestamp_started\":1637573250,\"timestamp_ended\":1637573257,\"attacker_id\":\"\",\"attacker_name\":\"\",\"attacker_faction\":\"\",\"attacker_factionname\":\"\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Attacked\",\"stealthed\":1,\"respect\":0,\"chain\":230,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.41,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.34}},\"174563134\":{\"code\":\"8c2112ea4354757895a1fee01232cb4e\",\"timestamp_started\":1637574657,\"timestamp_ended\":1637574676,\"attacker_id\":227744,\"attacker_name\":\"RumbLE\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":0,\"respect\":0,\"chain\":262,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.41,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.35}},\"174573433\":{\"code\":\"b08175360fee877d7c9bb3f39e4f69ca\",\"timestamp_started\":1637584056,\"timestamp_ended\":1637584068,\"attacker_id\":557912,\"attacker_name\":\"All_in_Ken\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Mugged\",\"stealthed\":0,\"respect\":0,\"chain\":395,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.41,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.4}},\"174577194\":{\"code\":\"32bdb658e47d8a806cbd00dfbfc31a89\",\"timestamp_started\":1637586738,\"timestamp_ended\":1637586744,\"attacker_id\":\"\",\"attacker_name\":\"\",\"attacker_faction\":\"\",\"attacker_factionname\":\"\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Hospitalized\",\"stealthed\":1,\"respect\":0,\"chain\":0,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.41,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.4}},\"174589404\":{\"code\":\"482658c0222a945bf30c9c669b39f343\",\"timestamp_started\":1637596343,\"timestamp_ended\":1637596351,\"attacker_id\":1315845,\"attacker_name\":\"Billiken\",\"attacker_faction\":2013,\"attacker_factionname\":\"Subversive  Alliance\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Mugged\",\"stealthed\":0,\"respect\":0,\"chain\":1,\"raid\":1,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.41,\"war\":2,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174649206\":{\"code\":\"46646dd6a5b9d55cfdd0c9e934c102d2\",\"timestamp_started\":1637655642,\"timestamp_ended\":1637655670,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":2014128,\"defender_name\":\"Aundar\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Lost\",\"stealthed\":0,\"respect\":0,\"chain\":0,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174649229\":{\"code\":\"acba53947777fd1730cbcf6d22ab5f0d\",\"timestamp_started\":1637655682,\"timestamp_ended\":1637655704,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":2014128,\"defender_name\":\"Aundar\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.99,\"chain\":1,\"raid\":0,\"respect_gain\":3.99,\"respect_loss\":1,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174683826\":{\"code\":\"8553620dddf91e6d2ec43f441cb496af\",\"timestamp_started\":1637690945,\"timestamp_ended\":1637690951,\"attacker_id\":21955,\"attacker_name\":\"Charger\",\"attacker_faction\":16312,\"attacker_factionname\":\"39th Street Killers X\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":0,\"chain\":31718,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.41,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.88}},\"174750412\":{\"code\":\"5e0bc7e482a5bf43c37f97dc36ea2535\",\"timestamp_started\":1637754837,\"timestamp_ended\":1637754866,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":2014128,\"defender_name\":\"Aundar\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Lost\",\"stealthed\":0,\"respect\":0,\"chain\":0,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174750455\":{\"code\":\"579878bc1843cfc5ff9db15c3c82f06f\",\"timestamp_started\":1637754879,\"timestamp_ended\":1637754887,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":2014128,\"defender_name\":\"Aundar\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.99,\"chain\":1,\"raid\":0,\"respect_gain\":3.99,\"respect_loss\":1,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174754926\":{\"code\":\"885cf5aaca595d580321d776ef4839fb\",\"timestamp_started\":1637759534,\"timestamp_ended\":1637759747,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":2014128,\"defender_name\":\"Aundar\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Assist\",\"stealthed\":0,\"respect\":0,\"chain\":0,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174759895\":{\"code\":\"14359301e34d40d28567c71b1ac32d92\",\"timestamp_started\":1637764349,\"timestamp_ended\":1637764374,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":2014128,\"defender_name\":\"Aundar\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Lost\",\"stealthed\":0,\"respect\":0,\"chain\":0,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174759932\":{\"code\":\"0ed22f38d53510b3b111d5b7d22cef60\",\"timestamp_started\":1637764381,\"timestamp_ended\":1637764384,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":2014128,\"defender_name\":\"Aundar\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Attacked\",\"stealthed\":1,\"respect\":3.99,\"chain\":1,\"raid\":0,\"respect_gain\":3.99,\"respect_loss\":1,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174797178\":{\"code\":\"dee66cb49c5d857738528d79d2b9c911\",\"timestamp_started\":1637795990,\"timestamp_ended\":1637796001,\"attacker_id\":337027,\"attacker_name\":\"THEN00B\",\"attacker_faction\":3241,\"attacker_factionname\":\"JFK\",\"defender_id\":2114440,\"defender_name\":\"DeKleineKobini\",\"defender_faction\":33007,\"defender_factionname\":\"HAKA\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":0,\"chain\":9425,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":2.82,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1.74}},\"174841235\":{\"code\":\"9744f5d3313e31807fe73a21a315808e\",\"timestamp_started\":1637840396,\"timestamp_ended\":1637840416,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":487848,\"defender_name\":\"Ch0ppy\",\"defender_faction\":6924,\"defender_factionname\":\"JFR\",\"result\":\"Lost\",\"stealthed\":0,\"respect\":0,\"chain\":0,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174842599\":{\"code\":\"a05d5f77ce225c34365d0621f064d402\",\"timestamp_started\":1637842025,\"timestamp_ended\":1637842028,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":487848,\"defender_name\":\"Ch0ppy\",\"defender_faction\":6924,\"defender_factionname\":\"JFR\",\"result\":\"Lost\",\"stealthed\":0,\"respect\":0,\"chain\":0,\"raid\":0,\"respect_gain\":0,\"respect_loss\":0,\"modifiers\":{\"fair_fight\":1,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174843455\":{\"code\":\"e22196d696aeb8d23b1d4bb62e7b32dc\",\"timestamp_started\":1637843017,\"timestamp_ended\":1637843026,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":487848,\"defender_name\":\"Ch0ppy\",\"defender_faction\":6924,\"defender_factionname\":\"JFR\",\"result\":\"Hospitalized\",\"stealthed\":0,\"respect\":5.03,\"chain\":2,\"raid\":0,\"respect_gain\":5.03,\"respect_loss\":1.26,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1.25,\"chain_bonus\":1}},\"174903076\":{\"code\":\"8f2c2bfdb718e5b3401571aea72904bd\",\"timestamp_started\":1637908665,\"timestamp_ended\":1637908683,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":151416,\"defender_name\":\"Bubbs\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":4.05,\"chain\":1,\"raid\":0,\"respect_gain\":4.05,\"respect_loss\":1.01,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174904332\":{\"code\":\"7196585e2cbd2292cb734fbf7a77a689\",\"timestamp_started\":1637910350,\"timestamp_ended\":1637910357,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":151416,\"defender_name\":\"Bubbs\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":4.05,\"chain\":1,\"raid\":0,\"respect_gain\":4.05,\"respect_loss\":1.01,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"174910040\":{\"code\":\"76d1359697ee38d8ad5fa9cc36505033\",\"timestamp_started\":1637917892,\"timestamp_ended\":1637917898,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":151416,\"defender_name\":\"Bubbs\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":4.05,\"chain\":1,\"raid\":0,\"respect_gain\":4.05,\"respect_loss\":1.01,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}},\"175030493\":{\"code\":\"369af2abed7f325175d28693a03e640a\",\"timestamp_started\":1638002751,\"timestamp_ended\":1638002761,\"attacker_id\":2114440,\"attacker_name\":\"DeKleineKobini\",\"attacker_faction\":33007,\"attacker_factionname\":\"HAKA\",\"defender_id\":1863033,\"defender_name\":\"NoodlyPiggy\",\"defender_faction\":0,\"defender_factionname\":\"\",\"result\":\"Attacked\",\"stealthed\":0,\"respect\":3.12,\"chain\":1,\"raid\":0,\"respect_gain\":3.12,\"respect_loss\":0.78,\"modifiers\":{\"fair_fight\":3,\"war\":1,\"retaliation\":1,\"group_attack\":1,\"overseas\":1,\"chain_bonus\":1}}}}");

        // Act
        Map<Long, Attacks> result = UserMapper.ofAttacks(json);

        // Assert
        Attacks.Modifiers attack1Modifiers = new Attacks.Modifiers();
        attack1Modifiers.setFairFight(2.4);
        attack1Modifiers.setWar(2);
        attack1Modifiers.setRetaliation(1);
        attack1Modifiers.setGroupAttack(1);
        attack1Modifiers.setOverseas(1);
        attack1Modifiers.setChainBonus(1.13);

        Attacks attack1 = new Attacks();
        attack1.setCode("51d4d4db0d303be93b6fe3aa3cb28205");
        attack1.setTimestampStarted(LocalDateTime.of(2021, 11, 15, 10, 35, 34));
        attack1.setTimestampEnded(LocalDateTime.of(2021, 11, 15, 10, 35, 44));
        attack1.setAttackerId(1151690);
        attack1.setAttackerName("sharkeyfive");
        attack1.setAttackerFaction(2013);
        attack1.setAttackerFactionName("Subversive  Alliance");
        attack1.setDefenderId(2114440);
        attack1.setDefenderName("DeKleineKobini");
        attack1.setDefenderFaction(33007);
        attack1.setDefenderFactionName("HAKA");
        attack1.setResult(Attacks.AttackResult.HOSPITALIZED);
        attack1.setStealthed(false);
        attack1.setRespect(0);
        attack1.setChain(34);
        attack1.setRaid(true);
        attack1.setRespectGain(0);
        attack1.setRespectLoss(0);
        attack1.setModifiers(attack1Modifiers);

        Attacks.Modifiers attack2Modifiers = new Attacks.Modifiers();
        attack2Modifiers.setFairFight(3);
        attack2Modifiers.setWar(1);
        attack2Modifiers.setRetaliation(1);
        attack2Modifiers.setGroupAttack(1);
        attack2Modifiers.setOverseas(1);
        attack2Modifiers.setChainBonus(1);

        Attacks attack2 = new Attacks();
        attack2.setCode("f5bb3b9042a6958a94a08a8ab2b533b9");
        attack2.setTimestampStarted(LocalDateTime.of(2021, 11, 17, 19, 26, 55));
        attack2.setTimestampEnded(LocalDateTime.of(2021, 11, 17, 19, 27, 2));
        attack2.setAttackerId(2114440);
        attack2.setAttackerName("DeKleineKobini");
        attack2.setAttackerFaction(33007);
        attack2.setAttackerFactionName("HAKA");
        attack2.setDefenderId(215524);
        attack2.setDefenderName("B1ade");
        attack2.setDefenderFaction(0);
        attack2.setDefenderFactionName("");
        attack2.setResult(Attacks.AttackResult.ATTACKED);
        attack2.setStealthed(false);
        attack2.setRespect(3.81);
        attack2.setChain(9);
        attack2.setRaid(false);
        attack2.setRespectGain(3.81);
        attack2.setRespectLoss(0.95);
        attack2.setModifiers(attack2Modifiers);

        assertThat(result)
                .hasSize(100)
                .containsEntry(173744624L, attack1)
                .containsEntry(173949571L, attack2);
    }

    @Test
    void ofBars() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"server_time\":1637502830,\"happy\":{\"current\":4425,\"maximum\":4525,\"increment\":5,\"interval\":900,\"ticktime\":370,\"fulltime\":17470},\"life\":{\"current\":6633,\"maximum\":6633,\"increment\":397,\"interval\":300,\"ticktime\":70,\"fulltime\":0},\"energy\":{\"current\":75,\"maximum\":150,\"increment\":5,\"interval\":600,\"ticktime\":370,\"fulltime\":8770},\"nerve\":{\"current\":33,\"maximum\":105,\"increment\":1,\"interval\":300,\"ticktime\":70,\"fulltime\":21370},\"chain\":{\"current\":0,\"maximum\":25000,\"timeout\":0,\"modifier\":1,\"cooldown\":0}}");

        // Act
        Bars bars = UserMapper.ofBars(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(bars.getServerTime()).isEqualTo(LocalDateTime.of(2021, 11, 21, 13, 53, 50));

        softly.assertThat(bars.getHappy().getCurrent()).isEqualTo(4425);
        softly.assertThat(bars.getHappy().getMaximum()).isEqualTo(4525);
        softly.assertThat(bars.getHappy().getIncrement()).isEqualTo(5);
        softly.assertThat(bars.getHappy().getInterval()).hasMinutes(15);
        softly.assertThat(bars.getHappy().getTicktime()).hasSeconds(370);
        softly.assertThat(bars.getHappy().getFulltime()).hasSeconds(17470);

        softly.assertThat(bars.getLife().getCurrent()).isEqualTo(6633);
        softly.assertThat(bars.getLife().getMaximum()).isEqualTo(6633);
        softly.assertThat(bars.getLife().getIncrement()).isEqualTo(397);
        softly.assertThat(bars.getLife().getInterval()).hasMinutes(5);
        softly.assertThat(bars.getLife().getTicktime()).hasSeconds(70);
        softly.assertThat(bars.getLife().getFulltime()).hasSeconds(0);

        softly.assertThat(bars.getEnergy().getCurrent()).isEqualTo(75);
        softly.assertThat(bars.getEnergy().getMaximum()).isEqualTo(150);
        softly.assertThat(bars.getEnergy().getIncrement()).isEqualTo(5);
        softly.assertThat(bars.getEnergy().getInterval()).hasMinutes(10);
        softly.assertThat(bars.getEnergy().getTicktime()).hasSeconds(370);
        softly.assertThat(bars.getEnergy().getFulltime()).hasMinutes(146);

        softly.assertThat(bars.getNerve().getCurrent()).isEqualTo(33);
        softly.assertThat(bars.getNerve().getMaximum()).isEqualTo(105);
        softly.assertThat(bars.getNerve().getIncrement()).isEqualTo(1);
        softly.assertThat(bars.getNerve().getInterval()).hasMinutes(5);
        softly.assertThat(bars.getNerve().getTicktime()).hasSeconds(70);
        softly.assertThat(bars.getNerve().getFulltime()).hasMinutes(356);

        softly.assertThat(bars.getChain().getCurrent()).isEqualTo(0);
        softly.assertThat(bars.getChain().getMaximum()).isEqualTo(25000);
        softly.assertThat(bars.getChain().getTimeout()).isEqualTo(0);
        softly.assertThat(bars.getChain().getModifier()).isEqualTo(1);
        softly.assertThat(bars.getChain().getCooldown()).isEqualTo(0);

        softly.assertAll();
    }

    @Test
    void ofBasic() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"level\":94,\"gender\":\"Male\",\"player_id\":2114440,\"name\":\"DeKleineKobini\",\"status\":{\"description\":\"Okay\",\"details\":\"\",\"state\":\"Okay\",\"color\":\"green\",\"until\":0}}");

        // Act
        Basic basic = UserMapper.ofBasic(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(basic.getLevel()).isEqualTo(94);
        softly.assertThat(basic.getGender()).isEqualTo(Gender.MALE);
        softly.assertThat(basic.getPlayerId()).isEqualTo(2114440);
        softly.assertThat(basic.getName()).isEqualTo("DeKleineKobini");

        softly.assertThat(basic.getStatus()).isNotNull();
        softly.assertThat(basic.getStatus().getDescription()).isEqualTo("Okay");
        softly.assertThat(basic.getStatus().getDetails()).isEqualTo("");
        softly.assertThat(basic.getStatus().getState()).isEqualTo(Status.State.OKAY);
        softly.assertThat(basic.getStatus().getColor()).isEqualTo("green");
        softly.assertThat(basic.getStatus().getUntil()).isEqualTo(Instant.ofEpochSecond(0).atOffset(ZoneOffset.UTC).toLocalDateTime());

        softly.assertAll();
    }

    @Test
    void ofBattleStats() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"strength\":1,\"speed\":2,\"dexterity\":3,\"defense\":4,\"total\":10,\"strength_modifier\":5,\"defense_modifier\":5,\"speed_modifier\":5,\"dexterity_modifier\":5,\"strength_info\":[\"+30% to Strength from Merits\",\"+5% to Strength from Education\",\"+1% to Strength from Faction\",\"-14% to Strength from Drug\"],\"defense_info\":[\"+30% to Defense from Merits\",\"+11% to Defense from Education\",\"-18% to Defense from Drug\"],\"speed_info\":[\"+30% to Speed from Merits\",\"+14% to Speed from Education\",\"+1% to Speed from Faction\",\"-25% to Speed from Drug\"],\"dexterity_info\":[\"+30% to Dexterity from Merits\",\"+19% to Dexterity from Education\"]}");

        // Act
        BattleStats stats = UserMapper.ofBattleStats(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(stats).isNotNull();
        softly.assertThat(stats.getStrength()).isEqualTo(1);
        softly.assertThat(stats.getSpeed()).isEqualTo(2);
        softly.assertThat(stats.getDexterity()).isEqualTo(3);
        softly.assertThat(stats.getDefense()).isEqualTo(4);
        softly.assertThat(stats.getTotal()).isEqualTo(10);
        softly.assertThat(stats.getStrengthModifier()).isEqualTo(5);
        softly.assertThat(stats.getDefenseModifier()).isEqualTo(5);
        softly.assertThat(stats.getSpeedModifier()).isEqualTo(5);
        softly.assertThat(stats.getDexterityModifier()).isEqualTo(5);
        softly.assertThat(stats.getStrengthInfo()).hasSize(4);
        softly.assertThat(stats.getDefenseInfo()).hasSize(3);
        softly.assertThat(stats.getSpeedInfo()).hasSize(4);
        softly.assertThat(stats.getDexterityInfo()).hasSize(2);

        softly.assertAll();
    }

    @Test
    void ofBattleStats_TopPlayer() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"strength\":2166691479,\"speed\":1364171599,\"dexterity\":1506931433,\"defense\":520976069394180,\"total\":520981107188691,\"strength_modifier\":5,\"defense_modifier\":5,\"speed_modifier\":5,\"dexterity_modifier\":5,\"strength_info\":[\"+30% to Strength from Merits\",\"+5% to Strength from Education\",\"+1% to Strength from Faction\",\"-14% to Strength from Drug\"],\"defense_info\":[\"+30% to Defense from Merits\",\"+11% to Defense from Education\",\"-18% to Defense from Drug\"],\"speed_info\":[\"+30% to Speed from Merits\",\"+14% to Speed from Education\",\"+1% to Speed from Faction\",\"-25% to Speed from Drug\"],\"dexterity_info\":[\"+30% to Dexterity from Merits\",\"+19% to Dexterity from Education\"]}");

        // Act
        BattleStats stats = UserMapper.ofBattleStats(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(stats).isNotNull();
        softly.assertThat(stats.getStrength()).isEqualTo(2166691479L);
        softly.assertThat(stats.getSpeed()).isEqualTo(1364171599L);
        softly.assertThat(stats.getDexterity()).isEqualTo(1506931433);
        softly.assertThat(stats.getDefense()).isEqualTo(520976069394180L);
        softly.assertThat(stats.getTotal()).isEqualTo(520981107188691L);
        softly.assertThat(stats.getStrengthModifier()).isEqualTo(5);
        softly.assertThat(stats.getDefenseModifier()).isEqualTo(5);
        softly.assertThat(stats.getSpeedModifier()).isEqualTo(5);
        softly.assertThat(stats.getDexterityModifier()).isEqualTo(5);
        softly.assertThat(stats.getStrengthInfo()).hasSize(4);
        softly.assertThat(stats.getDefenseInfo()).hasSize(3);
        softly.assertThat(stats.getSpeedInfo()).hasSize(4);
        softly.assertThat(stats.getDexterityInfo()).hasSize(2);

        softly.assertAll();
    }

    @Test
    void ofBazaar() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"bazaar\":[{\"ID\":477,\"name\":\"Black Easter Egg\",\"type\":\"Candy\",\"quantity\":3,\"price\":250000,\"market_price\":166925},{\"ID\":472,\"name\":\"Blue Easter Egg\",\"type\":\"Candy\",\"quantity\":2,\"price\":250000,\"market_price\":167132},{\"ID\":97,\"name\":\"Bunch of Flowers\",\"type\":\"Flower\",\"quantity\":27459,\"price\":700,\"market_price\":245},{\"ID\":48,\"name\":\"Jacket\",\"type\":\"Clothing\",\"quantity\":216151,\"price\":699,\"market_price\":237},{\"ID\":584,\"name\":\"Orange Easter Egg\",\"type\":\"Candy\",\"quantity\":4,\"price\":175000,\"market_price\":164125},{\"ID\":585,\"name\":\"Pink Easter Egg\",\"type\":\"Candy\",\"quantity\":3,\"price\":175000,\"market_price\":166375},{\"ID\":474,\"name\":\"Red Easter Egg\",\"type\":\"Candy\",\"quantity\":3,\"price\":175000,\"market_price\":155189},{\"ID\":203,\"name\":\"Shrooms\",\"type\":\"Drug\",\"quantity\":65,\"price\":24999,\"market_price\":9999},{\"ID\":475,\"name\":\"Yellow Easter Egg\",\"type\":\"Candy\",\"quantity\":1,\"price\":175000,\"market_price\":168683}]}");

        // Act
        List<Bazaar> bazaar = UserMapper.ofBazaar(json);

        // Assert
        Bazaar jacket = new Bazaar();
        jacket.setId(48);
        jacket.setName("Jacket");
        jacket.setType("Clothing");
        jacket.setQuantity(216151);
        jacket.setPrice(699);
        jacket.setMarketPrice(237);

        Bazaar bunchOfFlowers = new Bazaar();
        bunchOfFlowers.setId(97);
        bunchOfFlowers.setName("Bunch of Flowers");
        bunchOfFlowers.setType("Flower");
        bunchOfFlowers.setQuantity(27459);
        bunchOfFlowers.setPrice(700);
        bunchOfFlowers.setMarketPrice(245);

        assertThat(bazaar)
                .hasSize(9)
                .contains(jacket, bunchOfFlowers);
    }

    @Test
    void ofCooldowns() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"cooldowns\":{\"drug\":27122,\"medical\":0,\"booster\":130277}}");

        // Act
        Cooldowns cooldowns = UserMapper.ofCooldowns(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(cooldowns.getDrug()).hasHours(7);
        softly.assertThat(cooldowns.getMedical()).hasSeconds(0);
        softly.assertThat(cooldowns.getBooster()).hasHours(36);

        softly.assertAll();
    }

    @Test
    void ofCrimes() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"criminalrecord\":{\"selling_illegal_products\":5001,\"theft\":2630,\"auto_theft\":208,\"drug_deals\":10003,\"computer_crimes\":10019,\"murder\":9407,\"fraud_crimes\":2551,\"other\":5043,\"total\":44862}}");

        // Act
        Crimes crimes = UserMapper.ofCrimes(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(crimes.getSellingIllegalProducts()).isEqualTo(5001);
        softly.assertThat(crimes.getTheft()).isEqualTo(2630);
        softly.assertThat(crimes.getAutoTheft()).isEqualTo(208);
        softly.assertThat(crimes.getDrugDeals()).isEqualTo(10003);
        softly.assertThat(crimes.getComputerCrimes()).isEqualTo(10019);
        softly.assertThat(crimes.getMurder()).isEqualTo(9407);
        softly.assertThat(crimes.getFraudCrimes()).isEqualTo(2551);
        softly.assertThat(crimes.getOther()).isEqualTo(5043);
        softly.assertThat(crimes.getTotal()).isEqualTo(44862);

        softly.assertAll();
    }

    @Test
    void ofDiscord() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"discord\":{\"userID\":2114440,\"discordID\":\"215959283605569537\"}}");

        // Act
        Discord discord = UserMapper.ofDiscord(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(discord.getUserId()).isEqualTo(2114440);
        softly.assertThat(discord.getDiscordId()).isEqualTo("215959283605569537");

        softly.assertAll();
    }

    @Test
    void ofDisplay() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"display\":[{\"ID\":634,\"name\":\"Bag of Bloody Eyeballs\",\"type\":\"Candy\",\"quantity\":1,\"circulation\":54795,\"market_price\":44178},{\"ID\":37,\"name\":\"Bag of Bon Bons\",\"type\":\"Candy\",\"quantity\":11,\"circulation\":1708490,\"market_price\":577},{\"ID\":527,\"name\":\"Bag of Candy Kisses\",\"type\":\"Candy\",\"quantity\":35,\"circulation\":818191,\"market_price\":26193},{\"ID\":529,\"name\":\"Bag of Chocolate Truffles\",\"type\":\"Candy\",\"quantity\":27,\"circulation\":972508,\"market_price\":85945},{\"ID\":1039,\"name\":\"Bag of Humbugs\",\"type\":\"Candy\",\"quantity\":5,\"circulation\":11974,\"market_price\":364142},{\"ID\":556,\"name\":\"Bag of Reindeer Droppings\",\"type\":\"Candy\",\"quantity\":25,\"circulation\":519304,\"market_price\":86979},{\"ID\":528,\"name\":\"Bag of Tootsie Rolls\",\"type\":\"Candy\",\"quantity\":24,\"circulation\":950421,\"market_price\":46049},{\"ID\":181,\"name\":\"Bottle of Champagne\",\"type\":\"Alcohol\",\"quantity\":27,\"circulation\":599084,\"market_price\":3942},{\"ID\":638,\"name\":\"Bottle of Christmas Cocktail\",\"type\":\"Alcohol\",\"quantity\":2,\"circulation\":280545,\"market_price\":189394},{\"ID\":873,\"name\":\"Bottle of Green Stout\",\"type\":\"Alcohol\",\"quantity\":4,\"circulation\":49793,\"market_price\":901798},{\"ID\":550,\"name\":\"Bottle of Kandy Kane\",\"type\":\"Alcohol\",\"quantity\":6,\"circulation\":493263,\"market_price\":85046},{\"ID\":551,\"name\":\"Bottle of Minty Mayhem\",\"type\":\"Alcohol\",\"quantity\":3,\"circulation\":514896,\"market_price\":169295},{\"ID\":552,\"name\":\"Bottle of Mistletoe Madness\",\"type\":\"Alcohol\",\"quantity\":5,\"circulation\":507926,\"market_price\":372733},{\"ID\":531,\"name\":\"Bottle of Pumpkin Brew\",\"type\":\"Alcohol\",\"quantity\":4,\"circulation\":536716,\"market_price\":85798},{\"ID\":541,\"name\":\"Bottle of Stinky Swamp Punch\",\"type\":\"Alcohol\",\"quantity\":1,\"circulation\":196059,\"market_price\":374163},{\"ID\":38,\"name\":\"Box of Bon Bons\",\"type\":\"Candy\",\"quantity\":26,\"circulation\":464735,\"market_price\":1077},{\"ID\":35,\"name\":\"Box of Chocolate Bars\",\"type\":\"Candy\",\"quantity\":1,\"circulation\":3161637,\"market_price\":525},{\"ID\":39,\"name\":\"Box of Extra Strong Mints\",\"type\":\"Candy\",\"quantity\":2,\"circulation\":2848723,\"market_price\":556},{\"ID\":209,\"name\":\"Box of Sweet Hearts\",\"type\":\"Candy\",\"quantity\":2,\"circulation\":2737116,\"market_price\":579},{\"ID\":586,\"name\":\"Jawbreaker\",\"type\":\"Candy\",\"quantity\":23,\"circulation\":70464,\"market_price\":270163},{\"ID\":310,\"name\":\"Lollipop\",\"type\":\"Candy\",\"quantity\":401,\"circulation\":45010089,\"market_price\":592},{\"ID\":870,\"name\":\"Love Juice\",\"type\":\"Drug\",\"quantity\":2,\"circulation\":32491,\"market_price\":14931665},{\"ID\":1143,\"name\":\"Medical Mask\",\"type\":\"Clothing\",\"quantity\":1,\"circulation\":2000,\"market_price\":0},{\"ID\":151,\"name\":\"Pixie Sticks\",\"type\":\"Candy\",\"quantity\":4,\"circulation\":207253,\"market_price\":265828}]}");

        // Act
        List<Display> result = UserMapper.ofDisplay(json);

        // Assert
        Display candyKisses = new Display();
        candyKisses.setId(527);
        candyKisses.setName("Bag of Candy Kisses");
        candyKisses.setType("Candy");
        candyKisses.setQuantity(35);
        candyKisses.setCirculation(818191);
        candyKisses.setMarketPrice(26193);

        Display medicalMask = new Display();
        medicalMask.setId(1143);
        medicalMask.setName("Medical Mask");
        medicalMask.setType("Clothing");
        medicalMask.setQuantity(1);
        medicalMask.setCirculation(2000);
        medicalMask.setMarketPrice(0);

        assertThat(result)
                .hasSize(24)
                .contains(candyKisses, medicalMask);
    }

    @Test
    void ofEducation() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"education_current\":28,\"education_timeleft\":403870,\"education_completed\":[1,14,15,16,17,18,19,20,21,22,24,25,26,27,32,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,112,113,116,119,120,125,126,127]}");

        // Act
        Education education = UserMapper.ofEducation(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(education.getCurrent()).isEqualTo(28);
        softly.assertThat(education.getTimeLeft()).hasHours(112);
        softly.assertThat(education.getCompleted())
                .hasSize(100)
                .contains(1L, 18L, 35L, 127L);

        softly.assertAll();
    }

    @Test
    void ofEvents() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"events\":{\"873413362\":{\"timestamp\":1636918561,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1629262\\\">themainman</a>, the director of <a href = \\\"http://www.torn.com/joblist.php#/p=corpinfo&ID=86818\\\">Jux @ Law</a>, has trained you from the company. You gained 50 INT and 25 END. \",\"seen\":1},\"873432165\":{\"timestamp\":1636922700,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=23707\\\">Hate_Train</a> attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=ee0c9b2aeaea3c3cfbb9050fc69b92b9\\\">view</a>]\",\"seen\":1},\"873492386\":{\"timestamp\":1636935447,\"event\":\"<a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=33007\\\">HAKA</a> has initiated an assault on <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=20747\\\">Damage Inc</a> over the sovereignty of <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/city.php#terrName=DIA\\\">DIA</a>\",\"seen\":1},\"873533362\":{\"timestamp\":1636944306,\"event\":\"The trade with <a href = \\\"http://www.torn.com/profiles.php?XID=2158905\\\">Johanmans10</a> has expired\",\"seen\":1},\"873537559\":{\"timestamp\":1636945326,\"event\":\"<a class=\\\"t-red bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=42125\\\">Octogenarian DirtyBombers</a> failed in its assault against <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=33007\\\">HAKA</a> over the sovereignty of <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/city.php#terrName=DQB\\\">DQB</a> [<a class=\\\"t-green\\\" href = \\\"http://www.torn.com/war.php?step=warreport&warID=27067\\\">view</a>]\",\"seen\":1},\"873599481\":{\"timestamp\":1636962043,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=2134338\\\">Ignas</a> attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=adc2701ae0456c48d1bcd3e056c2afbf\\\">view</a>]\",\"seen\":1},\"873641009\":{\"timestamp\":1636972544,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1151690\\\">sharkeyfive</a> attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=51d4d4db0d303be93b6fe3aa3cb28205\\\">view</a>]\",\"seen\":1},\"873688339\":{\"timestamp\":1636984625,\"event\":\"<a href = http://www.torn.com/bank.php>Your bank investment has ended. Please click here to collect your funds.</a>\",\"seen\":1},\"873702160\":{\"timestamp\":1636987635,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1456295\\\">Nepy</a> attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=88be3b0f34c7bfb517bf2faa7ec458dd\\\">view</a>]\",\"seen\":1},\"873716871\":{\"timestamp\":1636991295,\"event\":\"Someone attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=e049a2370da258c8b6bac54fd958acc3\\\">view</a>]\",\"seen\":1},\"873717156\":{\"timestamp\":1636991352,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1602158\\\">Queequeg</a> attacked you but <a href = \\\"http://www.torn.com/profiles.php?XID=1643018\\\">MagicDust</a> fought him off [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=3df4f6324c2e8e699aa77b6511a2e779\\\">view</a>]\",\"seen\":1},\"873717340\":{\"timestamp\":1636991402,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1335768\\\">nerbas</a> attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=c620566455566d5c8ed0d23edf6fe271\\\">view</a>]\",\"seen\":1},\"873717654\":{\"timestamp\":1636991461,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1335768\\\">nerbas</a> attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=bddc7ba29c3fcb76b416bb50c560df0d\\\">view</a>]\",\"seen\":1},\"873719107\":{\"timestamp\":1636991783,\"event\":\"Someone attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=edf48b30dd2d920a58cd54374ca2aeb5\\\">view</a>]\",\"seen\":1},\"873719889\":{\"timestamp\":1636991971,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1161983\\\">TwoEleven</a> attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=f20bf11d782d6fc089f52757bf9f83e1\\\">view</a>]\",\"seen\":1},\"873720515\":{\"timestamp\":1636992092,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=220641\\\">Twigman</a> attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=9db85495fb860c1c26051759c95ff4c4\\\">view</a>]\",\"seen\":1},\"873720797\":{\"timestamp\":1636992171,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=220641\\\">Twigman</a> attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=7ebbdc9851adf42790504b017e625f16\\\">view</a>]\",\"seen\":1},\"873721447\":{\"timestamp\":1636992360,\"event\":\"Someone attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=37fbd5aedb8600f7adf23a3310e90a22\\\">view</a>]\",\"seen\":1},\"873729662\":{\"timestamp\":1636994247,\"event\":\"You were sent a Drug Pack from <a href = http://www.torn.com/profiles.php?XID=2584133>Saiditohe</a> with the message: Thank You\",\"seen\":1},\"873742218\":{\"timestamp\":1636997105,\"event\":\"<a class=\\\"t-red bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=14768\\\">RISE FROM RUIN</a> failed in its assault against <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=33007\\\">HAKA</a> over the sovereignty of <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/city.php#terrName=BQB\\\">BQB</a> [<a class=\\\"t-green\\\" href = \\\"http://www.torn.com/war.php?step=warreport&warID=27095\\\">view</a>]\",\"seen\":1},\"873758442\":{\"timestamp\":1637000525,\"event\":\"<a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=33007\\\">HAKA</a> successfully assaulted <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=20747\\\">Damage Inc</a> and claimed sovereignty over <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/city.php#terrName=DIA\\\">DIA</a> [<a class=\\\"t-green\\\" href = \\\"http://www.torn.com/war.php?step=warreport&warID=27282\\\">view</a>]\",\"seen\":1},\"873765660\":{\"timestamp\":1637001867,\"event\":\"The territorial war between <a class=\\\"bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=33007\\\">HAKA</a> and <a class=\\\"bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=42125\\\">Octogenarian DirtyBombers</a> over the sovereignty of <a class=\\\"bold\\\" href = \\\"http://www.torn.com/city.php#terrName=COB\\\">COB</a> has ended in a truce. [<a class=\\\"t-green\\\" href = \\\"http://www.torn.com/war.php?step=warreport&warID=27119\\\">view</a>]\",\"seen\":1},\"873768201\":{\"timestamp\":1637002360,\"event\":\"<a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=33007\\\">HAKA</a> has initiated an assault on <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=20747\\\">Damage Inc</a> over the sovereignty of <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/city.php#terrName=GIA\\\">GIA</a>\",\"seen\":1},\"873809817\":{\"timestamp\":1637010873,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1689701\\\">miro</a> attacked you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=2d764857d26c1be8f7d1d2b41e5da5c2\\\">view</a>]\",\"seen\":1},\"873823280\":{\"timestamp\":1637013610,\"event\":\"The trade with <a href = \\\"http://www.torn.com/profiles.php?XID=2158905\\\">Johanmans10</a> has expired\",\"seen\":1},\"873844748\":{\"timestamp\":1637018453,\"event\":\"The territorial war between <a class=\\\"bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=15644\\\">BoW - New Sith Order</a> and <a class=\\\"bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=33007\\\">HAKA</a> over the sovereignty of <a class=\\\"bold\\\" href = \\\"http://www.torn.com/city.php#terrName=EOB\\\">EOB</a> has ended in a truce. [<a class=\\\"t-green\\\" href = \\\"http://www.torn.com/war.php?step=warreport&warID=27132\\\">view</a>]\",\"seen\":1},\"873858246\":{\"timestamp\":1637021106,\"event\":\"Stock dividends from ASS, EWM, FHG, LSC, MUN, PRN, SYM and THS are now available for withdrawal from the <a href = \\\"http://www.torn.com/page.php?sid=stocks\\\">Stock Exchange.</a>\",\"seen\":1},\"873862306\":{\"timestamp\":1637021307,\"event\":\"<a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=33007\\\">HAKA</a> has initiated an assault on <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=6780\\\">BoW - The United</a> over the sovereignty of <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/city.php#terrName=WHA\\\">WHA</a>\",\"seen\":1},\"873862633\":{\"timestamp\":1637021345,\"event\":\"<a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=33007\\\">HAKA</a> successfully assaulted <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=2013\\\">Subversive  Alliance</a> and claimed sovereignty over <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/city.php#terrName=MKF\\\">MKF</a> [<a class=\\\"t-green\\\" href = \\\"http://www.torn.com/war.php?step=warreport&warID=27223\\\">view</a>]\",\"seen\":1},\"873862890\":{\"timestamp\":1637021362,\"event\":\"<a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=33007\\\">HAKA</a> has initiated an assault on <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=2013\\\">Subversive  Alliance</a> over the sovereignty of <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/city.php#terrName=KPB\\\">KPB</a>\",\"seen\":1},\"873949397\":{\"timestamp\":1637043412,\"event\":\"You and your team successfully hijacked a plane! View the details <a href = \\\"http://www.torn.com/organisedcrimes.php?step=log&ID=9562088\\\">here</a>!\",\"seen\":1},\"873949432\":{\"timestamp\":1637043422,\"event\":\"You have been selected by <a href = \\\"http://www.torn.com/profiles.php?XID=2167520\\\" class=\\\"h\\\">Zortok</a> to participate in an organized crime. You, along with 7 others will make up the team to <a href = \\\"http://www.torn.com/organisedcrimes.php\\\">hijack a plane</a> in 168 hours.\",\"seen\":1},\"873988916\":{\"timestamp\":1637055725,\"event\":\"<a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=33007\\\">HAKA</a> successfully assaulted <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=20747\\\">Damage Inc</a> and claimed sovereignty over <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/city.php#terrName=GIA\\\">GIA</a> [<a class=\\\"t-green\\\" href = \\\"http://www.torn.com/war.php?step=warreport&warID=27331\\\">view</a>]\",\"seen\":1},\"874053929\":{\"timestamp\":1637073965,\"event\":\"<a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=33007\\\">HAKA</a> successfully assaulted <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=2013\\\">Subversive  Alliance</a> and claimed sovereignty over <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/city.php#terrName=KPB\\\">KPB</a> [<a class=\\\"t-green\\\" href = \\\"http://www.torn.com/war.php?step=warreport&warID=27352\\\">view</a>]\",\"seen\":1},\"874059505\":{\"timestamp\":1637075345,\"event\":\"<a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=33007\\\">HAKA</a> successfully assaulted <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=6780\\\">BoW - The United</a> and claimed sovereignty over <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/city.php#terrName=WHA\\\">WHA</a> [<a class=\\\"t-green\\\" href = \\\"http://www.torn.com/war.php?step=warreport&warID=27351\\\">view</a>]\",\"seen\":1},\"874062233\":{\"timestamp\":1637075882,\"event\":\"<a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=33007\\\">HAKA</a> has initiated an assault on <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=2013\\\">Subversive  Alliance</a> over the sovereignty of <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/city.php#terrName=YLB\\\">YLB</a>\",\"seen\":1},\"874074804\":{\"timestamp\":1637079240,\"event\":\"You were sent  Duke's Safe from <a href = \\\"http://www.torn.com/profiles.php?XID=4\\\">DUKE</a>\",\"seen\":1},\"874126614\":{\"timestamp\":1637091130,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=667086\\\">ripp3r</a> attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=466de201fa5da38460de791d7529976c\\\">view</a>]\",\"seen\":1},\"874127023\":{\"timestamp\":1637091233,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=667086\\\">ripp3r</a> attacked you but <a href = \\\"http://www.torn.com/profiles.php?XID=2220399\\\">Wade</a> fought him off [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=cefe91588193d8d428896f13164a3241\\\">view</a>]\",\"seen\":1},\"874151344\":{\"timestamp\":1637096408,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=719629\\\">ThaBishop</a> attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=3875de3bc45d39075e77dbc6e53bf631\\\">view</a>]\",\"seen\":1},\"874209626\":{\"timestamp\":1637109399,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=2236830\\\">BINADAM</a> won a class action lawsuit on your behalf. You have been credited with <b>$174,272</b>.\",\"seen\":1},\"874224751\":{\"timestamp\":1637113298,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1629262\\\">themainman</a>, the director of <a href = \\\"http://www.torn.com/joblist.php#/p=corpinfo&ID=86818\\\">Jux @ Law</a>, has trained you from the company. You gained 50 INT and 25 END. \",\"seen\":1},\"874224782\":{\"timestamp\":1637113305,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1629262\\\">themainman</a>, the director of <a href = \\\"http://www.torn.com/joblist.php#/p=corpinfo&ID=86818\\\">Jux @ Law</a>, has trained you from the company. You gained 50 INT and 25 END. \",\"seen\":1},\"874331975\":{\"timestamp\":1637145986,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=719629\\\">ThaBishop</a> mugged you and stole $71,069 [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=843bef9e69df9b8ab51c0d10a3895c48\\\">view</a>]\",\"seen\":1},\"874333431\":{\"timestamp\":1637146506,\"event\":\"<a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=33007\\\">HAKA</a> successfully assaulted <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/factions.php?step=profile&ID=2013\\\">Subversive  Alliance</a> and claimed sovereignty over <a class=\\\"t-green bold\\\" href = \\\"http://www.torn.com/city.php#terrName=YLB\\\">YLB</a> [<a class=\\\"t-green\\\" href = \\\"http://www.torn.com/war.php?step=warreport&warID=27408\\\">view</a>]\",\"seen\":1},\"874352298\":{\"timestamp\":1637152489,\"event\":\"<a href = http://www.torn.com/profiles.php?XID=590683>tin-lizzy</a> successfully busted you out of jail.\",\"seen\":1},\"874362163\":{\"timestamp\":1637155498,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=111337\\\">PxKSnowball</a> attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=814f54c9030527058d90ae22183d28c1\\\">view</a>]\",\"seen\":1},\"874362530\":{\"timestamp\":1637155600,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=111337\\\">PxKSnowball</a> attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=487eb7a8e4eb4bffebdf4d3292adde85\\\">view</a>]\",\"seen\":1},\"874406611\":{\"timestamp\":1637167106,\"event\":\"<a href = http://www.torn.com/profiles.php?XID=1302645>Kylar_Stern</a> canceled the Plane hijacking crime that you were selected for.\",\"seen\":1},\"874460478\":{\"timestamp\":1637179209,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=667086\\\">ripp3r</a> mugged you and stole $66,502 [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=5dc32765e986fa9aa33d2d69fa66deeb\\\">view</a>]\",\"seen\":1},\"874495674\":{\"timestamp\":1637186777,\"event\":\"You have been selected by <a href = \\\"http://www.torn.com/profiles.php?XID=2167520\\\" class=\\\"h\\\">Zortok</a> to participate in an organized crime. You, along with 7 others will make up the team to <a href = \\\"http://www.torn.com/organisedcrimes.php\\\">hijack a plane</a> in 168 hours.\",\"seen\":1},\"874658821\":{\"timestamp\":1637231707,\"event\":\"You were sent a Honda NSX from <a href = http://www.torn.com/profiles.php?XID=2648076>zace66</a> with the message: thanks for busting me out of jail - please accept this car as its the one I was stealing when i got caught ;)\",\"seen\":1},\"874822997\":{\"timestamp\":1637273561,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1629262\\\">themainman</a>, the director of <a href = \\\"http://www.torn.com/joblist.php#/p=corpinfo&ID=86818\\\">Jux @ Law</a>, has trained you from the company. You gained 50 INT and 25 END. \",\"seen\":1},\"874823044\":{\"timestamp\":1637273570,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1629262\\\">themainman</a>, the director of <a href = \\\"http://www.torn.com/joblist.php#/p=corpinfo&ID=86818\\\">Jux @ Law</a>, has trained you from the company. You gained 50 INT and 25 END. \",\"seen\":1},\"874829176\":{\"timestamp\":1637275240,\"event\":\"You were sent a Box of Sweet Hearts from <a href = http://www.torn.com/profiles.php?XID=2731217>Esdeath2887</a>\",\"seen\":1},\"875034447\":{\"timestamp\":1637333454,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=2732550\\\">Wilcox2103</a> hospitalized <a href = \\\"http://www.torn.com/profiles.php?XID=2503791\\\">-Coy-</a> and earned your $1 bounty reward\",\"seen\":1},\"875276627\":{\"timestamp\":1637378493,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=227744\\\">RumbLE</a> mugged you and stole $87,512 [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=e97e8aff89eb81f87c60f31ac9304971\\\">view</a>]\",\"seen\":1},\"875307756\":{\"timestamp\":1637385989,\"event\":\"Someone mugged you and stole $15,757 [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=f32c4419fb18ade816e60d8261d4bbdb\\\">view</a>]\",\"seen\":1},\"875565865\":{\"timestamp\":1637439171,\"event\":\"<a href = http://www.torn.com/profiles.php?XID=238477>BenW</a> successfully busted you out of jail.\",\"seen\":1},\"875583877\":{\"timestamp\":1637442400,\"event\":\"You were sent $3,000,000 from <a href = http://www.torn.com/profiles.php?XID=2179462>Brev</a>.\",\"seen\":1},\"875590370\":{\"timestamp\":1637443722,\"event\":\"You were sent $6,969,696 from <a href = http://www.torn.com/profiles.php?XID=445764>Silver</a>.\",\"seen\":1},\"875678301\":{\"timestamp\":1637459742,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1629262\\\">themainman</a>, the director of <a href = \\\"http://www.torn.com/joblist.php#/p=corpinfo&ID=86818\\\">Jux @ Law</a>, has trained you from the company. You gained 50 INT and 25 END. \",\"seen\":1},\"875678352\":{\"timestamp\":1637459750,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1629262\\\">themainman</a>, the director of <a href = \\\"http://www.torn.com/joblist.php#/p=corpinfo&ID=86818\\\">Jux @ Law</a>, has trained you from the company. You gained 50 INT and 25 END. \",\"seen\":1},\"875863679\":{\"timestamp\":1637506206,\"event\":\"You were sent $1,000,000 from <a href = http://www.torn.com/profiles.php?XID=2093418>Traxler</a>.\",\"seen\":1},\"875864011\":{\"timestamp\":1637506275,\"event\":\"You were sent $1,000,000 from <a href = http://www.torn.com/profiles.php?XID=2093418>Traxler</a>.\",\"seen\":1},\"875874505\":{\"timestamp\":1637509030,\"event\":\"<a href = http://www.torn.com/profiles.php?XID=2650260>Coddfish</a> bought 1 x Donator Pack from your bazaar for $23,390,000.\",\"seen\":1},\"875874741\":{\"timestamp\":1637509102,\"event\":\"<a href = http://www.torn.com/profiles.php?XID=1989860>EKLA_vya</a> bought 1 x Donator Pack from your bazaar for $23,390,000.\",\"seen\":1},\"875910806\":{\"timestamp\":1637517706,\"event\":\"Someone  has placed a bounty on you with a reward of $1\",\"seen\":1},\"875978958\":{\"timestamp\":1637530207,\"event\":\"The trade with <a href = \\\"http://www.torn.com/profiles.php?XID=2158905\\\">Johanmans10</a> has expired\",\"seen\":1},\"875983797\":{\"timestamp\":1637531341,\"event\":\"Someone attacked you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=47246797d049fdbf1fe32066bb0c5ee1\\\">view</a>]\",\"seen\":1},\"875991687\":{\"timestamp\":1637533006,\"event\":\"Someone mugged you and stole $4,486,201 [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=5a1a0050283f28bfd844c4e2cb9e388f\\\">view</a>]\",\"seen\":1},\"876003838\":{\"timestamp\":1637535703,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1456295\\\">Nepy</a> attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=f0f6eac94d5f2f7e59fdf7cbb24b4b84\\\">view</a>]\",\"seen\":1},\"876005768\":{\"timestamp\":1637536174,\"event\":\"<a href = http://www.torn.com/profiles.php?XID=363281>goobs</a> successfully revived you.\",\"seen\":1},\"876006241\":{\"timestamp\":1637536303,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=234103\\\">Morcha</a> attacked you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=9fe87290e589e584e8bec510dca1f4ee\\\">view</a>]\",\"seen\":1},\"876017606\":{\"timestamp\":1637538985,\"event\":\"Someone attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=fa6ba5c583030e61fba50900e22fcf77\\\">view</a>]\",\"seen\":1},\"876086465\":{\"timestamp\":1637553819,\"event\":\"Someone  has placed a bounty on you with a reward of $1\",\"seen\":1},\"876095376\":{\"timestamp\":1637556307,\"event\":\"The trade with <a href = \\\"http://www.torn.com/profiles.php?XID=2455404\\\">Glackattack</a> has expired\",\"seen\":1},\"876131348\":{\"timestamp\":1637566532,\"event\":\"Someone attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=f468d76a4feaec9e627ad5028ac968b7\\\">view</a>]\",\"seen\":1},\"876153022\":{\"timestamp\":1637573257,\"event\":\"Someone attacked you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=d918dd8d4c65bf5f59da87ce89ad5882\\\">view</a>]\",\"seen\":1},\"876157473\":{\"timestamp\":1637574676,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=227744\\\">RumbLE</a> attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=8c2112ea4354757895a1fee01232cb4e\\\">view</a>]\",\"seen\":1},\"876192011\":{\"timestamp\":1637584068,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=557912\\\">All_in_Ken</a> mugged you and stole $985 [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=b08175360fee877d7c9bb3f39e4f69ca\\\">view</a>]\",\"seen\":1},\"876201070\":{\"timestamp\":1637586744,\"event\":\"Someone attacked and hospitalized you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=32bdb658e47d8a806cbd00dfbfc31a89\\\">view</a>]\",\"seen\":1},\"876208027\":{\"timestamp\":1637588532,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=2114440\\\">DeKleineKobini</a> loaned you 1x Combat Gloves from the faction armory.\",\"seen\":1},\"876208072\":{\"timestamp\":1637588549,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=2114440\\\">DeKleineKobini</a> loaned you 1x Combat Pants from the faction armory.\",\"seen\":1},\"876219102\":{\"timestamp\":1637591407,\"event\":\"The trade with <a href = \\\"http://www.torn.com/profiles.php?XID=2455404\\\">Glackattack</a> has expired\",\"seen\":1},\"876239203\":{\"timestamp\":1637596351,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1315845\\\">Billiken</a> mugged you and stole $536 [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=482658c0222a945bf30c9c669b39f343\\\">view</a>]\",\"seen\":1},\"876249178\":{\"timestamp\":1637598898,\"event\":\"You were sent $1,000,000 from <a href = http://www.torn.com/profiles.php?XID=2595709>Khal</a>.\",\"seen\":1},\"876338282\":{\"timestamp\":1637618410,\"event\":\"The trade with <a href = \\\"http://www.torn.com/profiles.php?XID=2158905\\\">Johanmans10</a> has expired\",\"seen\":1},\"876368203\":{\"timestamp\":1637625884,\"event\":\"Stock dividends from ASS, EWM, FHG, LSC, MCS, MUN, PRN, SYM and THS are now available for withdrawal from the <a href = \\\"http://www.torn.com/page.php?sid=stocks\\\">Stock Exchange.</a>\",\"seen\":1},\"876556896\":{\"timestamp\":1637678368,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1629262\\\">themainman</a>, the director of <a href = \\\"http://www.torn.com/joblist.php#/p=corpinfo&ID=86818\\\">Jux @ Law - Hiring</a>, has trained you from the company. You gained 50 INT and 25 END. \",\"seen\":1},\"876556933\":{\"timestamp\":1637678375,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1629262\\\">themainman</a>, the director of <a href = \\\"http://www.torn.com/joblist.php#/p=corpinfo&ID=86818\\\">Jux @ Law - Hiring</a>, has trained you from the company. You gained 50 INT and 25 END. \",\"seen\":1},\"876611518\":{\"timestamp\":1637690951,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=21955\\\">Charger</a> attacked you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=8553620dddf91e6d2ec43f441cb496af\\\">view</a>]\",\"seen\":1},\"877027121\":{\"timestamp\":1637796001,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=337027\\\">THEN00B</a> attacked you [<a href = \\\"http://www.torn.com/loader.php?sid=attackLog&ID=dee66cb49c5d857738528d79d2b9c911\\\">view</a>]\",\"seen\":1},\"877050242\":{\"timestamp\":1637801003,\"event\":\"You and your team successfully hijacked a plane! View the details <a href = \\\"http://www.torn.com/organisedcrimes.php?step=log&ID=9591614\\\">here</a>!\",\"seen\":1},\"877050303\":{\"timestamp\":1637801019,\"event\":\"You have been selected by <a href = \\\"http://www.torn.com/profiles.php?XID=2167520\\\" class=\\\"h\\\">Zortok</a> to participate in an organized crime. You, along with 7 others will make up the team to <a href = \\\"http://www.torn.com/organisedcrimes.php\\\">hijack a plane</a> in 168 hours.\",\"seen\":1},\"877526268\":{\"timestamp\":1637908654,\"event\":\"You were sent an Old Lady Mask from <a href = \\\"http://www.torn.com/profiles.php?XID=4\\\">DUKE</a>\",\"seen\":1},\"877526269\":{\"timestamp\":1637908654,\"event\":\"You were sent a Scarred Man Mask from <a href = \\\"http://www.torn.com/profiles.php?XID=4\\\">DUKE</a>\",\"seen\":1},\"877546904\":{\"timestamp\":1637912645,\"event\":\"<a href = http://www.torn.com/education.php>The education course you were taking has ended. Please click here.</a>\",\"seen\":1},\"877961378\":{\"timestamp\":1637976026,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1629262\\\">themainman</a>, the director of <a href = \\\"http://www.torn.com/joblist.php#/p=corpinfo&ID=86818\\\">Jux @ Law</a>, has trained you from the company. You gained 50 INT and 25 END. \",\"seen\":1},\"877961600\":{\"timestamp\":1637976033,\"event\":\"<a href = \\\"http://www.torn.com/profiles.php?XID=1629262\\\">themainman</a>, the director of <a href = \\\"http://www.torn.com/joblist.php#/p=corpinfo&ID=86818\\\">Jux @ Law</a>, has trained you from the company. You gained 50 INT and 25 END. \",\"seen\":1}}}");

        // Act
        Map<Long, Events> result = UserMapper.ofEvents(json);

        // Assert
        Events event1 = new Events();
        event1.setTimestamp(LocalDateTime.of(2021, 11, 15, 2, 45, 6));
        event1.setEvent("The trade with <a href = \"http://www.torn.com/profiles.php?XID=2158905\">Johanmans10</a> has expired");
        event1.setSeen(true);

        Events event2 = new Events();
        event2.setTimestamp(LocalDateTime.of(2021, 11, 21, 14, 50, 6));
        event2.setEvent("You were sent $1,000,000 from <a href = http://www.torn.com/profiles.php?XID=2093418>Traxler</a>.");
        event2.setSeen(true);

        assertThat(result)
                .hasSize(100)
                .containsEntry(873533362L, event1)
                .containsEntry(875863679L, event2);
    }

    @Test
    void ofGym() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"active_gym\":28}\n");

        // Act
        Gym gym = UserMapper.ofGym(json);

        // Assert
        assertThat(gym.getActiveGym()).isEqualTo(28);
    }

    @Test
    void ofHOF() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"halloffame\":{\"attacks\":{\"value\":9704,\"rank\":3183},\"battlestats\":{\"value\":1851378691,\"rank\":5878},\"busts\":{\"value\":3050,\"rank\":1773},\"defends\":{\"value\":1254,\"rank\":2272},\"networth\":{\"value\":24559486834,\"rank\":3139},\"offences\":{\"value\":44940,\"rank\":4375},\"revives\":{\"value\":2712,\"rank\":404},\"traveled\":{\"value\":772,\"rank\":16713},\"workstats\":{\"value\":209672,\"rank\":111392},\"level\":{\"value\":94,\"rank\":2462},\"rank\":{\"value\":23,\"rank\":3688},\"respect\":{\"value\":6334187,\"rank\":32}}}");

        // Act
        Map<String, HOF> result = UserMapper.ofHOF(json);

        // Assert
        HOF attacks = new HOF();
        attacks.setValue(9704);
        attacks.setRank(3183);

        HOF busts = new HOF();
        busts.setValue(3050);
        busts.setRank(1773);

        HOF revives = new HOF();
        revives.setValue(2712);
        revives.setRank(404);

        assertThat(result)
                .hasSize(12)
                .containsEntry(HOF.ATTACKS, attacks)
                .containsEntry(HOF.BUSTS, busts)
                .containsEntry(HOF.REVIVES, revives);
    }

    @Test
    void ofHonors() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"honors_awarded\":[1,3,4,6,7,8,9,10,11,12,13,15,16,17,18,19,20,21,22,23,25,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,53,55,56,57,60,61,63,64,130,131,133,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,155,157,159,161,162,163,164,212,215,216,217,220,221,222,226,227,228,229,230,232,233,234,236,238,239,240,241,242,243,244,245,246,247,248,249,251,252,253,254,255,256,257,258,259,266,267,268,269,270,271,272,273,274,277,281,283,284,288,297,298,308,309,313,315,316,317,318,322,326,327,338,367,371,375,395,398,406,414,418,427,431,437,443,459,475,477,478,481,488,497,500,505,506,513,515,517,519,520,521,525,534,537,541,542,543,544,546,548,549,552,566,567,571,572,581,601,605,606,607,608,615,617,627,629,631,635,636,639,640,641,643,646,651,653,656,659,665,670,678,679,686,687,694,700,708,716,717,719,720,721,723,729,730,731,734,739,740,742,743,763,781,791,793,800,827,828,838,839,843,844,845,846,853,860,863,870,871,873,882,888,896,902,903,906,916,942,943,944,945,951,955,964,969,1001,1002,1004,1007],\"honors_time\":[1536365631,1551268956,1601366806,1574253436,1558651738,1563101582,1614983287,1540859391,1528549915,1526237590,1585941368,1526487532,1552376677,1610801200,1526213054,1620108352,1524697899,1562771267,1564389418,1614271885,1528589663,1528830423,1537090617,1530292645,1533770106,1537449262,1554397807,1574345810,1571314771,1569742858,1544182324,1530643863,1540570916,1524334752,1524517397,1525765532,1526678145,1529884869,1530644174,1532215120,1547804292,1565310036,1575642453,1593127352,1627557846,1566971455,1579596119,1604678689,1614581874,1540421598,1596035577,1629916642,1551971126,1614670927,1529262841,1625996510,1625468139,1559465561,1584888002,1529584393,1530090456,1531766559,1528820677,1536914013,1532198601,1531297559,1530771867,1530642627,1531304597,1530465426,1530467775,1533160950,1607190307,1559724996,1589701880,1533237776,1623400017,1577549608,1591643024,1548371060,1601366806,1537938286,1619451326,1609730724,1534540801,1629495088,1604766805,1554654055,1537938286,1548548711,1610800586,1572989405,1527917774,1603284824,1527114727,1539076883,1580151496,1604488928,1527624750,1550756597,1556270269,1552381681,1554220937,1578677312,1537871610,1551081990,1532008147,1608924258,1631817909,1559219188,1600968542,1526856364,1525599006,1526856364,1530314836,1526856364,1614983287,1549648362,1563006995,1615459977,1528803592,1530554593,1606003774,1533123625,1528924597,1533695583,1578229384,1605599730,1556212093,1530824553,1556212087,1596011899,1606306350,1587817881,1587817879,1545696026,1600845581,1632392566,1605629881,1568196331,1606306346,1579105990,1535198715,1530883921,1526465629,1529429248,1525080426,1577838459,1528147995,1545440206,1529746006,1603897156,1530910486,1527198207,1526460200,1530503249,1540956579,1542240890,1543925224,1541694317,1547943790,1531846168,1575728746,1563824168,1578394694,1563372348,1581434616,1543429459,1543680789,1543938110,1555153385,1551195961,1564177429,1611947846,1530880905,1530040066,1553601869,1559473162,1568734007,1620108352,1567147726,1620108352,1528828127,1555984015,1583221656,1552985242,1575990962,1568156524,1599589026,1565334428,1563878856,1550608787,1549184496,1605687824,1530231223,1539090814,1565292380,1601845685,1575751802,1573301556,1593064022,1543679898,1577581045,1597764091,1563372351,1581238964,1576850355,1541523387,1636279653,1552376378,1556461062,1594058146,1553612171,1572294329,1536717750,1529510682,1542973849,1618759005,1557760480,1553612186,1553612098,1625933279,1534266478,1599552911,1538491433,1569393252,1569393256,1581669463,1569670565,1563895400,1593246516,1631359661,1574373301,1575617376,1580650453,1600158582,1593197350,1593196825,1597091526,1605732699,1592997284,1617256546,1588603833,1588686593,1591218013,1605448137,1591092511,1593705293,1600872353,1600872334,1595420638,1607583882,1594134123,1594996923,1596555563,1600931965,1598362254,1599570991,1604168654,1601384664,1606138691,1603310504,1601384688,1605627323,1602596657,1604268127,1603834639,1606834850,1606834850,1606834850,1618315610]}");

        // Act
        List<Honors> honors = UserMapper.ofHonors(json);

        // Assert
        Honors watchingYou = new Honors();
        watchingYou.setId(1);
        watchingYou.setAwarded(LocalDateTime.of(2018, 9, 8, 0, 13, 51));

        Honors killStreak500 = new Honors();
        killStreak500.setId(17);
        killStreak500.setAwarded(LocalDateTime.of(2021, 1, 16, 12, 46, 40));

        assertThat(honors)
                .hasSize(271)
                .contains(watchingYou, killStreak500);
    }

    @Test
    void ofIcons() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"icons\":{\"icon51\":\"Drug Cooldown - Under the influence of Xanax - 0 days, 1 hours, 41 minutes and 59 seconds\",\"icon38\":\"Stock Market - You own shares in the stock market\",\"icon19\":\"Education - Currently completing the Trigonometry course - 17 days, 16 hours, 25 minutes and 2 seconds\",\"icon85\":\"Organized Crime - Hijack a plane - REDACTED\",\"icon27\":\"Company - Attorney of Jux @ Law (Law Firm)\",\"icon35\":\"Bazaar - You have items in your bazaar\",\"icon68\":\"Reading Book - Yes Please Diabetes - Doubles candy effects for 31 days - REDACTED\",\"icon29\":\"Bank Investment - Current bank investment worth REDACTED - REDACTED\",\"icon9\":\"Faction - Thunderbird of HAKA\",\"icon8\":\"Married - ExKioz\",\"icon10\":\"Player Committee - Member\",\"icon4\":\"Subscriber - Donator status: REDACTED - Subscriber until: REDACTED\",\"icon6\":\"Male\"}}");

        // Act
        Map<String, String> result = UserMapper.ofIcons(json);

        // Assert
        assertThat(result)
                .hasSize(13)
                .containsEntry("icon6", "Male")
                .containsEntry("icon27", "Company - Attorney of Jux @ Law (Law Firm)")
                .containsEntry("icon35", "Bazaar - You have items in your bazaar")
                .containsEntry("icon38", "Stock Market - You own shares in the stock market");
    }

    @Test
    void ofInventory() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"inventory\":[{\"ID\":570,\"name\":\"Advanced Driving Manual\",\"type\":\"Enhancer\",\"quantity\":1,\"equipped\":0,\"market_price\":177315},{\"ID\":282,\"name\":\"African Violet\",\"type\":\"Flower\",\"quantity\":18,\"equipped\":0,\"market_price\":57842},{\"ID\":26,\"name\":\"AK-47\",\"type\":\"Primary\",\"quantity\":1,\"equipped\":0,\"market_price\":10569},{\"ID\":399,\"name\":\"ArmaLite M-15A4\",\"type\":\"Primary\",\"quantity\":1,\"equipped\":0,\"market_price\":36883333},{\"ID\":72,\"name\":\"Armored Virus\",\"type\":\"Virus\",\"quantity\":1,\"equipped\":0,\"market_price\":107790},{\"ID\":87,\"name\":\"Audi S4\",\"type\":\"Car\",\"quantity\":1,\"equipped\":0,\"market_price\":12441},{\"ID\":79,\"name\":\"Audi TT Quattro\",\"type\":\"Car\",\"quantity\":54,\"equipped\":0,\"market_price\":54028},{\"ID\":527,\"name\":\"Bag of Candy Kisses\",\"type\":\"Candy\",\"quantity\":1,\"equipped\":0,\"market_price\":26193},{\"ID\":528,\"name\":\"Bag of Tootsie Rolls\",\"type\":\"Candy\",\"quantity\":422,\"equipped\":0,\"market_price\":46049},{\"ID\":569,\"name\":\"Balaclava\",\"type\":\"Enhancer\",\"quantity\":1,\"equipped\":0,\"market_price\":702565},{\"ID\":2,\"name\":\"Baseball Bat\",\"type\":\"Melee\",\"quantity\":1,\"equipped\":3,\"market_price\":188},{\"ID\":327,\"name\":\"Blank Tokens\",\"type\":\"Other\",\"quantity\":1,\"equipped\":0,\"market_price\":42771},{\"ID\":732,\"name\":\"Blood Bag : A+\",\"type\":\"Medical\",\"quantity\":953,\"equipped\":0,\"market_price\":20031},{\"ID\":734,\"name\":\"Blood Bag : B+\",\"type\":\"Medical\",\"quantity\":170,\"equipped\":0,\"market_price\":19566},{\"ID\":1012,\"name\":\"Blood Bag : Irradiated\",\"type\":\"Medical\",\"quantity\":5,\"equipped\":0,\"market_price\":23583},{\"ID\":80,\"name\":\"BMW M5\",\"type\":\"Car\",\"quantity\":2,\"equipped\":0,\"market_price\":42539},{\"ID\":81,\"name\":\"BMW Z8\",\"type\":\"Car\",\"quantity\":49,\"equipped\":0,\"market_price\":79514},{\"ID\":747,\"name\":\"Book : A Job For Your Hands\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":776,\"name\":\"Book : Because I'm Happy - The Pharrell Story\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":744,\"name\":\"Book : Brawn Over Brains\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":786,\"name\":\"Book : Brown-nosing The Boss\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":772,\"name\":\"Book : Finders Keepers\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":782,\"name\":\"Book : Fuelling Your Way To Failure\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":757,\"name\":\"Book : Get Hard Or Go Home\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":780,\"name\":\"Book : Going Back For More\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":758,\"name\":\"Book : Gym Grunting - Shouting To Success\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":750,\"name\":\"Book : High School For Adults\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":774,\"name\":\"Book : Higher Daddy, Higher!\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":773,\"name\":\"Book : Hot Turkey\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":746,\"name\":\"Book : Keeping Your Face Handsome\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":761,\"name\":\"Book : Limbo Lovers 101\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":766,\"name\":\"Book : Mailing Yourself Abroad\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":785,\"name\":\"Book : Memories And Mammaries\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":751,\"name\":\"Book : Milk Yourself Sober\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":754,\"name\":\"Book : No Shame No Pain\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":755,\"name\":\"Book : Run Like The Wind\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":787,\"name\":\"Book : Running Away From Trouble\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":779,\"name\":\"Book : Self Control Is For Losers\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":769,\"name\":\"Book : Shawshank Sure Ain't For Me!\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":760,\"name\":\"Book : Speed 3 - The Rejected Script\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":775,\"name\":\"Book : The Real Dutch Courage\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":745,\"name\":\"Book : Time Is In The Mind\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":784,\"name\":\"Book : Ugly Energy\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":756,\"name\":\"Book : Weaseling Out Of Trouble\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":763,\"name\":\"Book : What Are Old Folk Good For Anyway?\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":771,\"name\":\"Book : Winking To Win\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":783,\"name\":\"Book : Yes Please Diabetes\",\"type\":\"Book\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":180,\"name\":\"Bottle of Beer\",\"type\":\"Alcohol\",\"quantity\":2935,\"equipped\":0,\"market_price\":4010},{\"ID\":426,\"name\":\"Bottle of Tequila\",\"type\":\"Alcohol\",\"quantity\":1,\"equipped\":0,\"market_price\":3939},{\"ID\":35,\"name\":\"Box of Chocolate Bars\",\"type\":\"Candy\",\"quantity\":1,\"equipped\":0,\"market_price\":525},{\"ID\":364,\"name\":\"Box of Grenades\",\"type\":\"Supply Pack\",\"quantity\":7,\"equipped\":0,\"market_price\":1079097},{\"ID\":365,\"name\":\"Box of Medical Supplies\",\"type\":\"Supply Pack\",\"quantity\":9,\"equipped\":0,\"market_price\":264319},{\"ID\":209,\"name\":\"Box of Sweet Hearts\",\"type\":\"Candy\",\"quantity\":1,\"equipped\":0,\"market_price\":579},{\"ID\":403,\"name\":\"Box of Tissues\",\"type\":\"Other\",\"quantity\":261,\"equipped\":0,\"market_price\":257},{\"ID\":394,\"name\":\"Brick\",\"type\":\"Temporary\",\"quantity\":758,\"equipped\":0,\"market_price\":411},{\"ID\":241,\"name\":\"Bushmaster Carbon 15\",\"type\":\"Primary\",\"quantity\":1,\"equipped\":0,\"market_price\":9863},{\"ID\":384,\"name\":\"Camel Plushie\",\"type\":\"Plushie\",\"quantity\":29,\"equipped\":0,\"market_price\":90423},{\"ID\":985,\"name\":\"Can of Goose Juice\",\"type\":\"Energy Drink\",\"quantity\":1,\"equipped\":0,\"market_price\":324641},{\"ID\":530,\"name\":\"Can of Munster\",\"type\":\"Energy Drink\",\"quantity\":2,\"equipped\":0,\"market_price\":1574843},{\"ID\":533,\"name\":\"Can of Taurine Elite\",\"type\":\"Energy Drink\",\"quantity\":1,\"equipped\":0,\"market_price\":4015024},{\"ID\":196,\"name\":\"Cannabis\",\"type\":\"Drug\",\"quantity\":367,\"equipped\":0,\"market_price\":6234},{\"ID\":271,\"name\":\"Ceibo Flower\",\"type\":\"Flower\",\"quantity\":1249,\"equipped\":0,\"market_price\":42253},{\"ID\":10,\"name\":\"Chainsaw\",\"type\":\"Melee\",\"quantity\":1,\"equipped\":0,\"market_price\":11186},{\"ID\":273,\"name\":\"Chamois Plushie\",\"type\":\"Plushie\",\"quantity\":1107,\"equipped\":0,\"market_price\":45547},{\"ID\":277,\"name\":\"Cherry Blossom\",\"type\":\"Flower\",\"quantity\":58,\"equipped\":0,\"market_price\":61452},{\"ID\":92,\"name\":\"Chevrolet Cavalier\",\"type\":\"Car\",\"quantity\":6,\"equipped\":0,\"market_price\":5136},{\"ID\":576,\"name\":\"Chloroform\",\"type\":\"Enhancer\",\"quantity\":1,\"equipped\":0,\"market_price\":125626},{\"ID\":494,\"name\":\"Citroen Saxo\",\"type\":\"Car\",\"quantity\":2,\"equipped\":0,\"market_price\":7956},{\"ID\":96,\"name\":\"Coat Hanger\",\"type\":\"Other\",\"quantity\":1,\"equipped\":0,\"market_price\":570949},{\"ID\":653,\"name\":\"Combat Boots\",\"type\":\"Defensive\",\"quantity\":1,\"equipped\":8,\"market_price\":2798374},{\"ID\":654,\"name\":\"Combat Gloves\",\"type\":\"Defensive\",\"quantity\":2,\"equipped\":9,\"market_price\":2715451},{\"ID\":652,\"name\":\"Combat Pants\",\"type\":\"Defensive\",\"quantity\":2,\"equipped\":7,\"market_price\":2792430},{\"ID\":643,\"name\":\"Construction Helmet\",\"type\":\"Defensive\",\"quantity\":1,\"equipped\":0,\"market_price\":13524},{\"ID\":263,\"name\":\"Crocus\",\"type\":\"Flower\",\"quantity\":58,\"equipped\":0,\"market_price\":10069},{\"ID\":567,\"name\":\"Cut-Throat Razor\",\"type\":\"Enhancer\",\"quantity\":1,\"equipped\":0,\"market_price\":180558},{\"ID\":260,\"name\":\"Dahlia\",\"type\":\"Flower\",\"quantity\":29,\"equipped\":0,\"market_price\":2723},{\"ID\":1049,\"name\":\"Denim Jacket\",\"type\":\"Clothing\",\"quantity\":1,\"equipped\":10,\"market_price\":0},{\"ID\":614,\"name\":\"Diamond Bladed Knife\",\"type\":\"Melee\",\"quantity\":27,\"equipped\":0,\"market_price\":800318},{\"ID\":83,\"name\":\"Dodge Charger\",\"type\":\"Car\",\"quantity\":92,\"equipped\":0,\"market_price\":68445},{\"ID\":283,\"name\":\"Donator Pack\",\"type\":\"Special\",\"quantity\":40,\"equipped\":0,\"market_price\":23020888},{\"ID\":129,\"name\":\"Dozen Roses\",\"type\":\"Flower\",\"quantity\":1,\"equipped\":0,\"market_price\":808},{\"ID\":370,\"name\":\"Drug Pack\",\"type\":\"Supply Pack\",\"quantity\":16,\"equipped\":0,\"market_price\":4239410},{\"ID\":21,\"name\":\"Dual 92G Berettas\",\"type\":\"Secondary\",\"quantity\":1,\"equipped\":2,\"market_price\":2971653},{\"ID\":578,\"name\":\"Duct Tape\",\"type\":\"Enhancer\",\"quantity\":1,\"equipped\":0,\"market_price\":121468},{\"ID\":272,\"name\":\"Edelweiss\",\"type\":\"Flower\",\"quantity\":67,\"equipped\":0,\"market_price\":31823},{\"ID\":731,\"name\":\"Empty Blood Bag\",\"type\":\"Medical\",\"quantity\":91,\"equipped\":0,\"market_price\":17288},{\"ID\":219,\"name\":\"Enfield SA-80\",\"type\":\"Primary\",\"quantity\":1,\"equipped\":0,\"market_price\":201939},{\"ID\":571,\"name\":\"Ergonomic Keyboard\",\"type\":\"Enhancer\",\"quantity\":1,\"equipped\":0,\"market_price\":264785},{\"ID\":366,\"name\":\"Erotic DVD\",\"type\":\"Booster\",\"quantity\":8,\"equipped\":0,\"market_price\":2193369},{\"ID\":574,\"name\":\"Fanny Pack\",\"type\":\"Enhancer\",\"quantity\":1,\"equipped\":0,\"market_price\":111271},{\"ID\":367,\"name\":\"Feathery Hotel Coupon\",\"type\":\"Booster\",\"quantity\":14,\"equipped\":0,\"market_price\":13747745},{\"ID\":67,\"name\":\"First Aid Kit\",\"type\":\"Medical\",\"quantity\":1138,\"equipped\":0,\"market_price\":8555},{\"ID\":178,\"name\":\"Flak Jacket\",\"type\":\"Defensive\",\"quantity\":1,\"equipped\":0,\"market_price\":4251},{\"ID\":255,\"name\":\"Flamethrower\",\"type\":\"Secondary\",\"quantity\":1,\"equipped\":0,\"market_price\":2525014},{\"ID\":222,\"name\":\"Flash Grenade\",\"type\":\"Temporary\",\"quantity\":1,\"equipped\":0,\"market_price\":50374},{\"ID\":85,\"name\":\"Ford GT40\",\"type\":\"Car\",\"quantity\":78,\"equipped\":0,\"market_price\":119653},{\"ID\":93,\"name\":\"Ford Mustang\",\"type\":\"Car\",\"quantity\":7,\"equipped\":0,\"market_price\":16539},{\"ID\":49,\"name\":\"Full Body Armor\",\"type\":\"Defensive\",\"quantity\":1,\"equipped\":4,\"market_price\":59428},{\"ID\":172,\"name\":\"Gas Can\",\"type\":\"Other\",\"quantity\":1,\"equipped\":0,\"market_price\":851027},{\"ID\":816,\"name\":\"Glass of Beer\",\"type\":\"Alcohol\",\"quantity\":16,\"equipped\":0,\"market_price\":0},{\"ID\":564,\"name\":\"Glasses\",\"type\":\"Enhancer\",\"quantity\":1,\"equipped\":0,\"market_price\":113878},{\"ID\":53,\"name\":\"Gold Ring\",\"type\":\"Jewelry\",\"quantity\":1,\"equipped\":0,\"market_price\":284},{\"ID\":588,\"name\":\"Goodie Bag\",\"type\":\"Supply Pack\",\"quantity\":1,\"equipped\":0,\"market_price\":32517331},{\"ID\":1009,\"name\":\"Halloween Basket : Horrifying\",\"type\":\"Special\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":1,\"name\":\"Hammer\",\"type\":\"Melee\",\"quantity\":1,\"equipped\":0,\"market_price\":79},{\"ID\":577,\"name\":\"Heavy Duty Padlock\",\"type\":\"Enhancer\",\"quantity\":2,\"equipped\":0,\"market_price\":101272},{\"ID\":242,\"name\":\"HEG\",\"type\":\"Temporary\",\"quantity\":25,\"equipped\":0,\"market_price\":15002},{\"ID\":565,\"name\":\"High-Speed Drive\",\"type\":\"Enhancer\",\"quantity\":1,\"equipped\":0,\"market_price\":100800},{\"ID\":95,\"name\":\"Holden SS\",\"type\":\"Car\",\"quantity\":12,\"equipped\":0,\"market_price\":24824},{\"ID\":89,\"name\":\"Honda Accord\",\"type\":\"Car\",\"quantity\":132,\"equipped\":0,\"market_price\":22817},{\"ID\":90,\"name\":\"Honda Civic\",\"type\":\"Car\",\"quantity\":3,\"equipped\":0,\"market_price\":8862},{\"ID\":78,\"name\":\"Honda NSX\",\"type\":\"Car\",\"quantity\":246,\"equipped\":0,\"market_price\":31819},{\"ID\":86,\"name\":\"Hummer H3\",\"type\":\"Car\",\"quantity\":4,\"equipped\":0,\"market_price\":19923},{\"ID\":252,\"name\":\"Ithaca 37\",\"type\":\"Primary\",\"quantity\":1,\"equipped\":0,\"market_price\":6304},{\"ID\":48,\"name\":\"Jacket\",\"type\":\"Clothing\",\"quantity\":2000,\"equipped\":0,\"market_price\":240},{\"ID\":223,\"name\":\"Jackhammer\",\"type\":\"Primary\",\"quantity\":1,\"equipped\":1,\"market_price\":4104882},{\"ID\":237,\"name\":\"Kodachi\",\"type\":\"Melee\",\"quantity\":2,\"equipped\":0,\"market_price\":84040},{\"ID\":154,\"name\":\"Laptop\",\"type\":\"Electronic\",\"quantity\":1,\"equipped\":0,\"market_price\":713852},{\"ID\":421,\"name\":\"Large Suitcase\",\"type\":\"Enhancer\",\"quantity\":1,\"equipped\":0,\"market_price\":10153820},{\"ID\":281,\"name\":\"Lion Plushie\",\"type\":\"Plushie\",\"quantity\":29,\"equipped\":0,\"market_price\":69646},{\"ID\":369,\"name\":\"Lottery Voucher\",\"type\":\"Supply Pack\",\"quantity\":8,\"equipped\":0,\"market_price\":913663},{\"ID\":29,\"name\":\"M16 A2 Rifle\",\"type\":\"Primary\",\"quantity\":1,\"equipped\":0,\"market_price\":35352},{\"ID\":31,\"name\":\"M249 SAW\",\"type\":\"Primary\",\"quantity\":1,\"equipped\":0,\"market_price\":684413},{\"ID\":225,\"name\":\"Mag 7\",\"type\":\"Primary\",\"quantity\":1,\"equipped\":0,\"market_price\":49991},{\"ID\":791,\"name\":\"Mediocre T-Shirt\",\"type\":\"Clothing\",\"quantity\":1,\"equipped\":0,\"market_price\":2013},{\"ID\":395,\"name\":\"Metal Nunchakus\",\"type\":\"Melee\",\"quantity\":1,\"equipped\":0,\"market_price\":346472},{\"ID\":63,\"name\":\"Minigun\",\"type\":\"Primary\",\"quantity\":1,\"equipped\":0,\"market_price\":1309365},{\"ID\":269,\"name\":\"Monkey Plushie\",\"type\":\"Plushie\",\"quantity\":1000,\"equipped\":0,\"market_price\":46554},{\"ID\":66,\"name\":\"Morphine\",\"type\":\"Medical\",\"quantity\":147,\"equipped\":0,\"market_price\":18035},{\"ID\":642,\"name\":\"Motorcycle Helmet\",\"type\":\"Defensive\",\"quantity\":1,\"equipped\":6,\"market_price\":33100434},{\"ID\":566,\"name\":\"Mountain Bike\",\"type\":\"Enhancer\",\"quantity\":1,\"equipped\":0,\"market_price\":443813},{\"ID\":266,\"name\":\"Nessie Plushie\",\"type\":\"Plushie\",\"quantity\":29,\"equipped\":0,\"market_price\":40083},{\"ID\":239,\"name\":\"Ninja Star\",\"type\":\"Temporary\",\"quantity\":1,\"equipped\":0,\"market_price\":1143},{\"ID\":524,\"name\":\"Nissan GT-R\",\"type\":\"Car\",\"quantity\":1,\"equipped\":0,\"market_price\":83800},{\"ID\":806,\"name\":\"Old Lady Mask\",\"type\":\"Clothing\",\"quantity\":1,\"equipped\":0,\"market_price\":24611},{\"ID\":264,\"name\":\"Orchid\",\"type\":\"Flower\",\"quantity\":29,\"equipped\":0,\"market_price\":29320},{\"ID\":201,\"name\":\"PCP\",\"type\":\"Drug\",\"quantity\":1,\"equipped\":0,\"market_price\":99690},{\"ID\":392,\"name\":\"Pepper Spray\",\"type\":\"Temporary\",\"quantity\":902,\"equipped\":0,\"market_price\":1733},{\"ID\":498,\"name\":\"Peugeot 106\",\"type\":\"Car\",\"quantity\":1,\"equipped\":0,\"market_price\":5492},{\"ID\":820,\"name\":\"Piggy Bank\",\"type\":\"Special\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":440,\"name\":\"Pillow\",\"type\":\"Melee\",\"quantity\":1,\"equipped\":0,\"market_price\":400571},{\"ID\":84,\"name\":\"Pontiac Firebird\",\"type\":\"Car\",\"quantity\":13,\"equipped\":0,\"market_price\":57455},{\"ID\":874,\"name\":\"Prototype\",\"type\":\"Secondary\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":248,\"name\":\"Qsz-92\",\"type\":\"Secondary\",\"quantity\":1,\"equipped\":0,\"market_price\":69917},{\"ID\":268,\"name\":\"Red Fox Plushie\",\"type\":\"Plushie\",\"quantity\":74,\"equipped\":0,\"market_price\":41147},{\"ID\":147,\"name\":\"Rusty Sword\",\"type\":\"Melee\",\"quantity\":1,\"equipped\":0,\"market_price\":74829},{\"ID\":811,\"name\":\"Scarred Man Mask\",\"type\":\"Clothing\",\"quantity\":2,\"equipped\":0,\"market_price\":26333},{\"ID\":573,\"name\":\"Screwdriver\",\"type\":\"Enhancer\",\"quantity\":2,\"equipped\":0,\"market_price\":203361},{\"ID\":186,\"name\":\"Sheep Plushie\",\"type\":\"Plushie\",\"quantity\":3000,\"equipped\":0,\"market_price\":727},{\"ID\":56,\"name\":\"Silver Necklace\",\"type\":\"Jewelry\",\"quantity\":1,\"equipped\":0,\"market_price\":1016},{\"ID\":709,\"name\":\"Silver Ribbon\",\"type\":\"Collectible\",\"quantity\":1,\"equipped\":0,\"market_price\":0},{\"ID\":183,\"name\":\"Single Red Rose\",\"type\":\"Flower\",\"quantity\":1,\"equipped\":0,\"market_price\":499},{\"ID\":817,\"name\":\"Six Pack of Alcohol\",\"type\":\"Supply Pack\",\"quantity\":8,\"equipped\":0,\"market_price\":1149974},{\"ID\":818,\"name\":\"Six Pack of Energy Drink\",\"type\":\"Supply Pack\",\"quantity\":8,\"equipped\":0,\"market_price\":15024596},{\"ID\":568,\"name\":\"Slim Crowbar\",\"type\":\"Enhancer\",\"quantity\":1,\"equipped\":0,\"market_price\":124682},{\"ID\":393,\"name\":\"Slingshot\",\"type\":\"Secondary\",\"quantity\":1,\"equipped\":0,\"market_price\":2200},{\"ID\":380,\"name\":\"Small Explosive Device\",\"type\":\"Special\",\"quantity\":1,\"equipped\":0,\"market_price\":2570133},{\"ID\":68,\"name\":\"Small First Aid Kit\",\"type\":\"Medical\",\"quantity\":507,\"equipped\":0,\"market_price\":4930},{\"ID\":226,\"name\":\"Smoke Grenade\",\"type\":\"Temporary\",\"quantity\":1,\"equipped\":0,\"market_price\":99930},{\"ID\":412,\"name\":\"Sports Shades\",\"type\":\"Clothing\",\"quantity\":1,\"equipped\":0,\"market_price\":3117},{\"ID\":59,\"name\":\"Stainless Steel Watch\",\"type\":\"Jewelry\",\"quantity\":1,\"equipped\":0,\"market_price\":3512},{\"ID\":73,\"name\":\"Stealth Virus\",\"type\":\"Virus\",\"quantity\":1,\"equipped\":0,\"market_price\":196172},{\"ID\":30,\"name\":\"Steyr AUG\",\"type\":\"Primary\",\"quantity\":12,\"equipped\":0,\"market_price\":74159},{\"ID\":834,\"name\":\"Sweatpants\",\"type\":\"Clothing\",\"quantity\":1,\"equipped\":10,\"market_price\":1001273},{\"ID\":256,\"name\":\"Tear Gas\",\"type\":\"Temporary\",\"quantity\":4,\"equipped\":0,\"market_price\":62130},{\"ID\":187,\"name\":\"Teddy Bear Plushie\",\"type\":\"Plushie\",\"quantity\":2000,\"equipped\":0,\"market_price\":742},{\"ID\":257,\"name\":\"Throwing Knife\",\"type\":\"Temporary\",\"quantity\":1,\"equipped\":0,\"market_price\":29394},{\"ID\":77,\"name\":\"Toyota MR2\",\"type\":\"Car\",\"quantity\":293,\"equipped\":0,\"market_price\":28591},{\"ID\":572,\"name\":\"Tracking Device\",\"type\":\"Enhancer\",\"quantity\":1,\"equipped\":0,\"market_price\":137361},{\"ID\":385,\"name\":\"Tribulus Omanense\",\"type\":\"Flower\",\"quantity\":19,\"equipped\":0,\"market_price\":75335},{\"ID\":616,\"name\":\"Trout\",\"type\":\"Temporary\",\"quantity\":366,\"equipped\":5,\"market_price\":19108},{\"ID\":575,\"name\":\"Tumble Dryer\",\"type\":\"Enhancer\",\"quantity\":1,\"equipped\":0,\"market_price\":106866},{\"ID\":205,\"name\":\"Vicodin\",\"type\":\"Drug\",\"quantity\":3,\"equipped\":0,\"market_price\":2072},{\"ID\":91,\"name\":\"Volkswagen Beetle\",\"type\":\"Car\",\"quantity\":5,\"equipped\":0,\"market_price\":7133},{\"ID\":579,\"name\":\"Wireless Dongle\",\"type\":\"Enhancer\",\"quantity\":1,\"equipped\":0,\"market_price\":204183},{\"ID\":261,\"name\":\"Wolverine Plushie\",\"type\":\"Plushie\",\"quantity\":1058,\"equipped\":0,\"market_price\":11472},{\"ID\":206,\"name\":\"Xanax\",\"type\":\"Drug\",\"quantity\":254,\"equipped\":0,\"market_price\":841716}]}");

        // Act
        List<Inventory> result = UserMapper.ofInventory(json);

        // Assert
        Inventory advancedDrivingManual = new Inventory();
        advancedDrivingManual.setId(570);
        advancedDrivingManual.setName("Advanced Driving Manual");
        advancedDrivingManual.setType("Enhancer");
        advancedDrivingManual.setQuantity(1);
        advancedDrivingManual.setEquipped(0);
        advancedDrivingManual.setMarketPrice(177315);

        Inventory balaclava = new Inventory();
        balaclava.setId(569);
        balaclava.setName("Balaclava");
        balaclava.setType("Enhancer");
        balaclava.setQuantity(1);
        balaclava.setEquipped(0);
        balaclava.setMarketPrice(702565);

        Inventory goodieBag = new Inventory();
        goodieBag.setId(588);
        goodieBag.setName("Goodie Bag");
        goodieBag.setType("Supply Pack");
        goodieBag.setQuantity(1);
        goodieBag.setEquipped(0);
        goodieBag.setMarketPrice(32517331);

        assertThat(result)
                .hasSize(177)
                .contains(advancedDrivingManual, balaclava, goodieBag);
    }

    @Test
    void ofJobPoints() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"jobpoints\":{\"jobs\":{\"law\":2},\"companies\":{\"1\":{\"name\":\"Hair Salon\",\"jobpoints\":9},\"2\":{\"name\":\"Law Firm\",\"jobpoints\":648},\"7\":{\"name\":\"Game Shop\",\"jobpoints\":58},\"10\":{\"name\":\"Adult Novelties\",\"jobpoints\":3},\"14\":{\"name\":\"Sweet Shop\",\"jobpoints\":1},\"16\":{\"name\":\"Television Network\",\"jobpoints\":24},\"20\":{\"name\":\"Property Broker\",\"jobpoints\":18},\"32\":{\"name\":\"Lingerie Store\",\"jobpoints\":5}}}}");

        // Act
        JobPoints result = UserMapper.ofJobPoints(json);

        // Assert
        JobPoints.CompanyPoints hairSalon = new JobPoints.CompanyPoints();
        hairSalon.setName("Hair Salon");
        hairSalon.setJobPoints(9);

        JobPoints.CompanyPoints adultNovelties = new JobPoints.CompanyPoints();
        adultNovelties.setName("Adult Novelties");
        adultNovelties.setJobPoints(3);

        JobPoints.CompanyPoints televisionNetwork = new JobPoints.CompanyPoints();
        televisionNetwork.setName("Television Network");
        televisionNetwork.setJobPoints(24);

        assertThat(result.getJobs())
                .hasSize(1)
                .containsEntry("law", 2L);

        assertThat(result.getCompanies())
                .hasSize(8)
                .containsEntry(1L, hairSalon)
                .containsEntry(10L, adultNovelties)
                .containsEntry(16L, televisionNetwork);
    }

    @Test
    void ofLog() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"log\":{\"tjCRrodPc4sKU27QV7Co\":{\"log\":7815,\"title\":\"Missions complete\",\"timestamp\":1639681245,\"category\":\"Missions\",\"data\":{\"type\":\"contract\",\"agent\":3,\"mission\":19,\"difficulty\":\"expert\",\"money\":56000,\"credits\":35},\"params\":{\"color\":\"green\"}},\"5YTWWLz7EVwPCUfeJt3g\":{\"log\":8155,\"title\":\"Attack mug\",\"timestamp\":1639681227,\"category\":\"Attacking\",\"data\":{\"defender\":302681,\"anonymous\":0,\"energy_used\":25,\"money_mugged\":8012,\"attackers\":1,\"chain\":1,\"log\":\"[<a href = \\\"/loader.php?sid=attackLog&ID=aaaa3013200f2112a8eaf11faa105f1a\\\"target = \\\"_self\\\">view</a>]\"},\"params\":{\"color\":\"green\"}},\"hbFmgDgszH03icVzuisT\":{\"log\":7800,\"title\":\"Missions accept\",\"timestamp\":1639681191,\"category\":\"Missions\",\"data\":{\"type\":\"contract\",\"agent\":3,\"mission\":19,\"difficulty\":\"expert\"},\"params\":[]},\"2G73iZoMsJTZkllpXoHV\":{\"log\":7900,\"title\":\"Missions buy reward item\",\"timestamp\":1639681028,\"category\":\"Missions\",\"data\":{\"item\":8,\"quantity\":1,\"credits_spent\":3},\"params\":{\"color\":\"green\"}},\"J4vyCI4eZiJzOo5AbrFT\":{\"log\":7900,\"title\":\"Missions buy reward item\",\"timestamp\":1639681025,\"category\":\"Missions\",\"data\":{\"item\":612,\"quantity\":1,\"credits_spent\":92},\"params\":{\"color\":\"green\"}},\"XuHCyDaZ6oVvFW6VKa5V\":{\"log\":7900,\"title\":\"Missions buy reward item\",\"timestamp\":1639681020,\"category\":\"Missions\",\"data\":{\"item\":226,\"quantity\":5,\"credits_spent\":64},\"params\":{\"color\":\"green\"}},\"0ebP80saS9TQVe8psd3M\":{\"log\":7910,\"title\":\"Missions buy reward mod\",\"timestamp\":1639681014,\"category\":\"Missions\",\"data\":{\"mod\":9,\"credits_spent\":518},\"params\":{\"color\":\"green\"}},\"v0o74rAsHG2oqLMZ3p0m\":{\"log\":7900,\"title\":\"Missions buy reward item\",\"timestamp\":1639681009,\"category\":\"Missions\",\"data\":{\"item\":391,\"quantity\":1,\"credits_spent\":22},\"params\":{\"color\":\"green\"}},\"tujsJ1I00W4WSRZNirim\":{\"log\":7900,\"title\":\"Missions buy reward item\",\"timestamp\":1639681007,\"category\":\"Missions\",\"data\":{\"item\":231,\"quantity\":1,\"credits_spent\":11},\"params\":{\"color\":\"green\"}},\"X2kL7PjqIH6F3klj6GzX\":{\"log\":7900,\"title\":\"Missions buy reward item\",\"timestamp\":1639681004,\"category\":\"Missions\",\"data\":{\"item\":254,\"quantity\":1,\"credits_spent\":9},\"params\":{\"color\":\"green\"}},\"jXg4lkBIhFQKyTtjtkvF\":{\"log\":6221,\"title\":\"Company employee pay\",\"timestamp\":1639678138,\"category\":\"Company\",\"data\":{\"pay\":0,\"job_points\":10,\"working_stats_received\":\"0,49,25\",\"company\":86818},\"params\":{\"italic\":1,\"color\":\"green\"}},\"V8aHvp78ofIx6NS8lTdk\":{\"log\":5360,\"title\":\"Bust success\",\"timestamp\":1639675556,\"category\":\"Jail\",\"data\":{\"user\":2028764,\"nerve_used\":3},\"params\":{\"color\":\"green\"}},\"VWrxD3i3TIEXBh9i486G\":{\"log\":5360,\"title\":\"Bust success\",\"timestamp\":1639675553,\"category\":\"Jail\",\"data\":{\"user\":2698799,\"nerve_used\":3},\"params\":{\"color\":\"green\"}},\"OiOdU5FuZAYcnHDyP0gG\":{\"log\":5360,\"title\":\"Bust success\",\"timestamp\":1639675552,\"category\":\"Jail\",\"data\":{\"user\":1765316,\"nerve_used\":3},\"params\":{\"color\":\"green\"}},\"yzztLBZCUkJDIQeqXYOo\":{\"log\":5725,\"title\":\"Crime success item gain\",\"timestamp\":1639675523,\"category\":\"Crimes\",\"data\":{\"crime\":69,\"nerve\":12,\"item_gained\":89},\"params\":{\"color\":\"green\"}},\"l2rVQnCT6Fc9Fz0XHzGo\":{\"log\":5725,\"title\":\"Crime success item gain\",\"timestamp\":1639675521,\"category\":\"Crimes\",\"data\":{\"crime\":69,\"nerve\":12,\"item_gained\":77},\"params\":{\"color\":\"green\"}},\"Gpl9EWlQlOipvsmu4jkI\":{\"log\":5302,\"title\":\"Gym train speed\",\"timestamp\":1639675517,\"category\":\"Gym\",\"data\":{\"trains\":1,\"energy_used\":25,\"speed_before\":\"547156478.92883\",\"speed_after\":547224052.2189759,\"speed_increased\":67573.290145848,\"happy_used\":10,\"gym\":26},\"params\":{\"color\":\"green\",\"changed\":1591103100}},\"ECCmsAirlXoai2JSoLbI\":{\"log\":6781,\"title\":\"Faction organized crimes plan receive\",\"timestamp\":1639675177,\"category\":\"Faction\",\"data\":{\"faction\":33007,\"planner\":601684,\"crime\":7,\"hours\":168},\"params\":{\"italic\":1}},\"2X4QleBFg1P8FQhmaFdH\":{\"log\":6791,\"title\":\"Faction organized crimes initiate receive\",\"timestamp\":1639675162,\"category\":\"Faction\",\"data\":{\"faction\":33007,\"initiator\":601684,\"crime\":7,\"result\":\"success\"},\"params\":{\"italic\":1}},\"uFefa7cdVaZvTY7lVA4c\":{\"log\":5302,\"title\":\"Gym train speed\",\"timestamp\":1639672298,\"category\":\"Gym\",\"data\":{\"trains\":3,\"energy_used\":75,\"speed_before\":\"546953725.89283\",\"speed_after\":547156478.9288287,\"speed_increased\":202753.03599865796,\"happy_used\":39,\"gym\":26},\"params\":{\"color\":\"green\",\"changed\":1591103100}},\"m8z5BUColXf0ltTrgZCh\":{\"log\":500,\"title\":\"Captcha validation success\",\"timestamp\":1639672294,\"category\":\"Captcha\",\"data\":{\"type\":\"image captcha\"},\"params\":[]},\"09x8kStHjh1C9ljSOiLE\":{\"log\":5360,\"title\":\"Bust success\",\"timestamp\":1639666144,\"category\":\"Jail\",\"data\":{\"user\":2551510,\"nerve_used\":3},\"params\":{\"color\":\"green\"}},\"lkHinKUdmAKGHtCpdHLI\":{\"log\":5360,\"title\":\"Bust success\",\"timestamp\":1639666142,\"category\":\"Jail\",\"data\":{\"user\":1875959,\"nerve_used\":3},\"params\":{\"color\":\"green\"}},\"JPkT8Aa4syY84xD2KmMM\":{\"log\":5362,\"title\":\"Bust failure\",\"timestamp\":1639666140,\"category\":\"Jail\",\"data\":{\"user\":2551510,\"nerve_used\":3},\"params\":{\"color\":\"red\"}},\"0CHcDxPHMG9rhllxD8bt\":{\"log\":101,\"title\":\"Successful login\",\"timestamp\":1639663973,\"category\":\"Authentication\",\"data\":{\"ip_address\":\"hidden\"},\"params\":[]},\"cuciZlxrmhLtW23ESciC\":{\"log\":5360,\"title\":\"Bust success\",\"timestamp\":1639663714,\"category\":\"Jail\",\"data\":{\"user\":2244170,\"nerve_used\":3},\"params\":{\"color\":\"green\"}},\"SvJphkN9L4kYhWYJnhbT\":{\"log\":5725,\"title\":\"Crime success item gain\",\"timestamp\":1639663707,\"category\":\"Crimes\",\"data\":{\"crime\":69,\"nerve\":12,\"item_gained\":78},\"params\":{\"color\":\"green\"}},\"Snv1Hc7cSZSEgey6R609\":{\"log\":5725,\"title\":\"Crime success item gain\",\"timestamp\":1639663705,\"category\":\"Crimes\",\"data\":{\"crime\":69,\"nerve\":12,\"item_gained\":77},\"params\":{\"color\":\"green\"}},\"Anh8QydDdsiNB2MgZNBG\":{\"log\":5725,\"title\":\"Crime success item gain\",\"timestamp\":1639663703,\"category\":\"Crimes\",\"data\":{\"crime\":69,\"nerve\":12,\"item_gained\":77},\"params\":{\"color\":\"green\"}},\"6kQOVgzGAsMAFM481Jhp\":{\"log\":5302,\"title\":\"Gym train speed\",\"timestamp\":1639663698,\"category\":\"Gym\",\"data\":{\"trains\":14,\"energy_used\":350,\"speed_before\":\"546006952.04564\",\"speed_after\":546953725.892831,\"speed_increased\":946773.84719095,\"happy_used\":171,\"gym\":26},\"params\":{\"color\":\"green\",\"changed\":1591103100}},\"7wp17k7kALBLjdyEjGyF\":{\"log\":500,\"title\":\"Captcha validation success\",\"timestamp\":1639663672,\"category\":\"Captcha\",\"data\":{\"type\":\"image captcha\"},\"params\":[]},\"OwHGS8czcf0XycxECHhR\":{\"log\":2290,\"title\":\"Item use xanax\",\"timestamp\":1639663669,\"category\":\"Drugs\",\"data\":{\"item\":206,\"faction\":0},\"params\":{\"color\":\"green\"}},\"mvSiijnTYMvCsggQG7kJ\":{\"log\":101,\"title\":\"Successful login\",\"timestamp\":1639652587,\"category\":\"Authentication\",\"data\":{\"ip_address\":\"hidden\"},\"params\":[]},\"DBxSAQxa2YIPDn3eYk2t\":{\"log\":5362,\"title\":\"Bust failure\",\"timestamp\":1639652426,\"category\":\"Jail\",\"data\":{\"user\":1699726,\"nerve_used\":3},\"params\":{\"color\":\"red\"}},\"2fqZOntDuqWjXubjAa3W\":{\"log\":5360,\"title\":\"Bust success\",\"timestamp\":1639652425,\"category\":\"Jail\",\"data\":{\"user\":2072739,\"nerve_used\":3},\"params\":{\"color\":\"green\"}},\"irChFpyJdwjpnCJd1LFL\":{\"log\":5360,\"title\":\"Bust success\",\"timestamp\":1639652396,\"category\":\"Jail\",\"data\":{\"user\":2575809,\"nerve_used\":3},\"params\":{\"color\":\"green\"}},\"k2BtcBSMC2N5WruXLkhC\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652391,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":1},\"params\":{\"color\":\"green\"}},\"fW98fT6lnQCCeRY8tHii\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652390,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":1},\"params\":{\"color\":\"green\"}},\"DnEkj2xCjBfDb4zUOdlL\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652390,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":2},\"params\":{\"color\":\"green\"}},\"TEOa577G8PBGhdP7okuc\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652390,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":2},\"params\":{\"color\":\"green\"}},\"TLHekin5ADIhO6CTSFVm\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652389,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":1},\"params\":{\"color\":\"green\"}},\"8DyGJ5chDk6MqiiA7Lox\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652389,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":1},\"params\":{\"color\":\"green\"}},\"mwt3AnImnbmkCbl43ANM\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652388,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":1},\"params\":{\"color\":\"green\"}},\"MufywIrPiZQIYdX17tK5\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652388,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":2},\"params\":{\"color\":\"green\"}},\"0LR4qNC5VjEJBw1Qqg9L\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652388,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":1},\"params\":{\"color\":\"green\"}},\"JArSFPVxbDCI8xvjecV6\":{\"log\":5360,\"title\":\"Bust success\",\"timestamp\":1639652381,\"category\":\"Jail\",\"data\":{\"user\":2319550,\"nerve_used\":3},\"params\":{\"color\":\"green\"}},\"TvrPxS1YYH2C1LFrFucv\":{\"log\":5360,\"title\":\"Bust success\",\"timestamp\":1639652379,\"category\":\"Jail\",\"data\":{\"user\":1586073,\"nerve_used\":3},\"params\":{\"color\":\"green\"}},\"RfiHhp2cyJKSW5tOG07j\":{\"log\":5360,\"title\":\"Bust success\",\"timestamp\":1639652378,\"category\":\"Jail\",\"data\":{\"user\":1907251,\"nerve_used\":3},\"params\":{\"color\":\"green\"}},\"qMznjEKcyonTIgXDzvpk\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652370,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":1},\"params\":{\"color\":\"green\"}},\"TMItDcKslRm026x7VlFh\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652369,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":1},\"params\":{\"color\":\"green\"}},\"yZywIlIJKBRwZ0EDsPNG\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652369,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":1},\"params\":{\"color\":\"green\"}},\"h3lJY4yhAMXBcAvGjXPh\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652368,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":1},\"params\":{\"color\":\"green\"}},\"gvDqLBbcrtUwrL3ZXnAO\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652368,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":1},\"params\":{\"color\":\"green\"}},\"HGt9PNJByDe9DTCgTpkP\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652368,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":1},\"params\":{\"color\":\"green\"}},\"C7bQWYJj4F8z1hmDHWpu\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652367,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":2},\"params\":{\"color\":\"green\"}},\"DJveVSIErcb8Vj94P36x\":{\"log\":2030,\"title\":\"Item use alcohol\",\"timestamp\":1639652367,\"category\":\"Item use\",\"data\":{\"item\":180,\"faction\":0,\"nerve_increased\":1},\"params\":{\"color\":\"green\"}},\"cV57u9mge02EuvgEmz67\":{\"log\":5725,\"title\":\"Crime success item gain\",\"timestamp\":1639652363,\"category\":\"Crimes\",\"data\":{\"crime\":69,\"nerve\":12,\"item_gained\":89},\"params\":{\"color\":\"green\"}},\"9V04XSR26yTKRuqNlVvW\":{\"log\":5725,\"title\":\"Crime success item gain\",\"timestamp\":1639652362,\"category\":\"Crimes\",\"data\":{\"crime\":69,\"nerve\":12,\"item_gained\":95},\"params\":{\"color\":\"green\"}},\"CeYx3n6dskD9xZadfCxM\":{\"log\":5725,\"title\":\"Crime success item gain\",\"timestamp\":1639652361,\"category\":\"Crimes\",\"data\":{\"crime\":69,\"nerve\":12,\"item_gained\":77},\"params\":{\"color\":\"green\"}},\"ZFXhWIAHgbEJW9fj0Kuo\":{\"log\":5725,\"title\":\"Crime success item gain\",\"timestamp\":1639652360,\"category\":\"Crimes\",\"data\":{\"crime\":69,\"nerve\":12,\"item_gained\":92},\"params\":{\"color\":\"green\"}},\"SXVSWaG5nEZzBGtMCWna\":{\"log\":5302,\"title\":\"Gym train speed\",\"timestamp\":1639652356,\"category\":\"Gym\",\"data\":{\"trains\":4,\"energy_used\":100,\"speed_before\":\"545736538.9536\",\"speed_after\":546006952.0456429,\"speed_increased\":270413.09204278595,\"happy_used\":45,\"gym\":26},\"params\":{\"color\":\"green\",\"changed\":1591103100}},\"cDCC8mYBt3fkCDYJ99Le\":{\"log\":7420,\"title\":\"Forums rate\",\"timestamp\":1639652301,\"category\":\"Forums\",\"data\":{\"thread\":16254659,\"forum\":44,\"post\":22025131,\"type\":\"post in\",\"rating\":\"liked\",\"author\":1979277},\"params\":[]},\"MVqOj9wA2TTjWkujTBNR\":{\"log\":7401,\"title\":\"Forums create post\",\"timestamp\":1639652297,\"category\":\"Forums\",\"data\":{\"thread\":16254659,\"forum\":44,\"post\":22025170},\"params\":[]},\"irffZmD4Zehv90Fz5lBq\":{\"log\":130,\"title\":\"2FA Authenticator code entry successful\",\"timestamp\":1639652242,\"category\":\"Authentication\",\"data\":{\"ip_address\":\"hidden\"},\"params\":[]},\"Z3FVc2gEXE2D4aH3g4HY\":{\"log\":313,\"title\":\"Authorised device add/remove\",\"timestamp\":1639652242,\"category\":\"Preferences\",\"data\":{\"result\":\"added\",\"device_removed\":\"Mobile (Android & Chrome)\"},\"params\":[]},\"oiultUlgbYtDxaFRf1cj\":{\"log\":7420,\"title\":\"Forums rate\",\"timestamp\":1639651524,\"category\":\"Forums\",\"data\":{\"thread\":16254556,\"forum\":2,\"post\":22023376,\"type\":\"post in\",\"rating\":\"disliked\",\"author\":1867864},\"params\":[]}}}\n");

        // Act
        Map<String, Log> result = UserMapper.ofLog(json);

        // Assert
        Map<String, Object> data1 = new HashMap<>();
        data1.put("user", 1765316);
        data1.put("nerve_used", 3);

        Log log1 = new Log();
        log1.setLog(5360);
        log1.setTitle("Bust success");
        log1.setTimestamp(LocalDateTime.of(2021, 12, 16, 17, 25, 52));
        log1.setCategory("Jail");
        log1.setData(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("type", "image captcha");

        Log log2 = new Log();
        log2.setLog(500);
        log2.setTitle("Captcha validation success");
        log2.setTimestamp(LocalDateTime.of(2021, 12, 16, 16, 31, 34));
        log2.setCategory("Captcha");
        log2.setData(data2);

        assertThat(result)
                .hasSize(66)
                .containsEntry("OiOdU5FuZAYcnHDyP0gG", log1)
                .containsEntry("m8z5BUColXf0ltTrgZCh", log2);
    }

    @Disabled("Not yet implemented")
    @Test
    void ofMedals() throws JsonProcessingException {
    }

    @Disabled("Not yet implemented")
    @Test
    void ofMerits() throws JsonProcessingException {
    }

    @Disabled("Not yet implemented")
    @Test
    void ofMessages() throws JsonProcessingException {
    }

    @Test
    void ofMoney() throws JsonProcessingException { // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"points\":14251,\"cayman_bank\":0,\"vault_amount\":0,\"company_funds\":0,\"daily_networth\":24502797128,\"money_onhand\":886911,\"city_bank\":{\"amount\":2398000000,\"time_left\":7073405}}");

        // Act
        Money money = UserMapper.ofMoney(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(money.getPoints()).isEqualTo(14251);
        softly.assertThat(money.getCaymanBank()).isEqualTo(0);
        softly.assertThat(money.getVault()).isEqualTo(0);
        softly.assertThat(money.getCompanyFunds()).isEqualTo(0);
        softly.assertThat(money.getDailyNetworth()).isEqualTo(24502797128L);
        softly.assertThat(money.getMoneyOnHand()).isEqualTo(886911);
        softly.assertThat(money.getCityBank().getAmount()).isEqualTo(2398000000L);
        softly.assertThat(money.getCityBank().getTimeLeft()).hasDays(81);

        softly.assertAll();
    }

    @Disabled("Not yet implemented")
    @Test
    void ofMissions() throws JsonProcessingException {
    }

    @Test
    void ofNetworth() throws JsonProcessingException { // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"networth\":{\"pending\":0,\"wallet\":886911,\"bank\":2398000000,\"points\":648277990,\"cayman\":0,\"vault\":0,\"piggybank\":3400000,\"items\":2356511851,\"displaycase\":56703412,\"bazaar\":55007420,\"itemmarket\":0,\"properties\":2929182000,\"stockmarket\":16109651000,\"auctionhouse\":0,\"company\":0,\"bookie\":0,\"enlistedcars\":16225460,\"loan\":0,\"unpaidfees\":0,\"total\":24573846044,\"parsetime\":0.2255561351776123}}");

        // Act
        Networth networth = UserMapper.ofNetworth(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(networth.getPending()).isEqualTo(0);
        softly.assertThat(networth.getWallet()).isEqualTo(886911);
        softly.assertThat(networth.getBank()).isEqualTo(2398000000L);
        softly.assertThat(networth.getPoints()).isEqualTo(648277990);
        softly.assertThat(networth.getCayman()).isEqualTo(0);
        softly.assertThat(networth.getVault()).isEqualTo(0);
        softly.assertThat(networth.getPiggyBank()).isEqualTo(3400000);
        softly.assertThat(networth.getItems()).isEqualTo(2356511851L);
        softly.assertThat(networth.getDisplayCase()).isEqualTo(56703412);
        softly.assertThat(networth.getBazaar()).isEqualTo(55007420);
        softly.assertThat(networth.getItemMarket()).isEqualTo(0);
        softly.assertThat(networth.getProperties()).isEqualTo(2929182000L);
        softly.assertThat(networth.getStockMarket()).isEqualTo(16109651000L);
        softly.assertThat(networth.getAuctionHouse()).isEqualTo(0);
        softly.assertThat(networth.getCompany()).isEqualTo(0);
        softly.assertThat(networth.getBookie()).isEqualTo(0);
        softly.assertThat(networth.getEnlistedCars()).isEqualTo(16225460);
        softly.assertThat(networth.getLoan()).isEqualTo(0);
        softly.assertThat(networth.getUnpaidFees()).isEqualTo(0);
        softly.assertThat(networth.getTotal()).isEqualTo(24573846044L);
        softly.assertThat(networth.getParseTime()).isEqualTo(0.2255561351776123);

        softly.assertAll();
    }

    @Disabled("Not yet implemented")
    @Test
    void ofNewEvents() throws JsonProcessingException {
    }

    @Disabled("Not yet implemented")
    @Test
    void ofNewMessages() throws JsonProcessingException {
    }

    @Disabled("Not yet implemented")
    @Test
    void ofNotifications() throws JsonProcessingException {
    }

    @Test
    void ofPerks() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"job_perks\":[\"+ Ability to revive\",\"- 10% Education length\"],\"property_perks\":[\"+ Access to airstrip\",\"+ 1% Life regeneration\",\"+ 2% Damage\",\"+ 2% Gym gains\"],\"stock_perks\":[\"10% Bank Interest Bonus (TCB)\"],\"merit_perks\":[\"+ 10 Maximum nerve\",\"+ 4% Critical hit rate\",\"+ 200% Awareness\",\"+ Bank interest rate\",\"+ 30% Strength\",\"+ 30% Speed\",\"+ 30% Dexterity\",\"+ 30% Defense\",\"+ 45% Life\",\"+ Rifle dmg/acc\"],\"education_perks\":[\"+ 10% Japanese blade damage\",\"+ 2% Melee damage\",\"+ Ability to find medieval coins\",\"+ Ability to find sculptures and Quran pages\",\"+ Ability to find senet board and amulets\",\"+ Museum unlocked\",\"+ 1% Damage\",\"+ 10% Neck hit damage\",\"- 25% Opponent stealth\",\"+ 3% Critical hit chance\",\"+ Medical booster equipping\",\"+ Pharmacy unlocked\",\"+ 1% Strength gym gains\",\"+ 1% Speed gym gains\",\"+ 1% Defense gym gains\",\"+ 1% Dexterity gym gains\",\"+ 10% Temporary booster effectiveness\",\"+ 1% Gym gains\",\"+ Coding of simple virus unlocked\",\"+ Coding of polymorphic and tunneling virus\",\"+ Hacking unlocked\",\"+ Coding of armored and stealth virus\",\"+ 10% Hacking success rate\",\"+ Anonymous mailing unlocked\",\"+ 10% Awareness\",\"+ 10% crime success rate\",\"+ Kick attack unlocked\",\"+ 100% Fist damage\",\"+ 1.00 Machine gun accuracy\",\"+ 1.00 SMG accuracy\",\"+ 1.00 Pistol accuracy\",\"+ 1.00 Rifle accuracy\",\"+ 1.00 Heavy artillery accuracy\",\"+ Weapon experience unlocked\",\"+ Jail escape chance\",\"+ Self bailing unlocked\",\"- 5% Estate agent cost\",\"- Escape jail nerve cost\",\"+ Company advertising effectiveness\",\"+ Driving crimes unlocked\",\"+ 1.00 Temporary weapon accuracy\",\"+ 5% Temporary weapon damage\",\"+ 15% Hunting bonus\",\"+ 1.00 Shotgun accuracy\",\"+ Sports shop unlocked\",\"+ Withdraw and deliver blood\",\"+ 1% Company productivity\",\"+ 5% Strength\",\"+ 14% Speed\",\"+ 19% Dexterity\",\"+ 11% Defense\",\"+ 20% Medical item effectiveness\",\"- 30% Virus coding time\",\"+ 65% Busting skill\",\"- 57.25% Bail cost\"],\"enhancer_perks\":[\"+ Search for cash success rate (Pair of Glasses)\",\"+ Bootlegging success rate (High-Speed DVD Drive)\",\"+ Shoplifting success rate (Mountain Bike)\",\"+ Pickpocketing success rate (Cut-Throat Razor)\",\"+ Larceny success rate (Slim Crowbar)\",\"+ Armed robbery success rate (Balaclava)\",\"+ Transport drugs success rate (Advanced Driving Tactics)\",\"+ Computer virus success rate (Ergonomic Keyboard)\",\"+ Assassination success rate (Tracking Device)\",\"+ Grand theft auto success rate (Screwdriver)\",\"+ Pawn shop success rate (Fanny Pack)\",\"+ Counterfeiting success rate (Tumble Dryer)\",\"+ Kidnapping success rate (Chloroform)\",\"+ Arms Trafficking success rate (Heavy Duty Padlock)\",\"+ Bombing success rate (Duct Tape)\",\"+ Hacking success rate (Wireless Dongle)\",\"+ 10% Church praying bonus (Rosary Beads)\",\"+ 4 Travel items (Large Suitcase)\"],\"company_perks\":[\"+ 50% decreased bail costs\",\"+ Easier to bust more people at once\",\"+ 20% organised crime skill\",\"+ View success chance of potential busts\"],\"faction_perks\":[\"+ Increases maximum nerve by 40\",\"+ Provides a 25% passive bonus to crime EXP\",\"+ Decreases all jail time by 28%\",\"+ Decreases bust nerve cost by 2\",\"+ Increase bust success chance by 50%\",\"+ Increases happy gain from candy by 50%\",\"+ Increases energy gain from energy drinks by 50%\",\"+ Adds 24 hours of maximum booster cooldown\",\"+ Increases nerve gain from alcohol by 50%\",\"+ Reduces the side effects of drugs and increases addiction mitigation by 30%\",\"+ Decreases risk of overdose by 30%\",\"+ Reduces drug addiction gain by 50%\",\"+ Increases maximum traveling capacity by 10\",\"+ Reduces rehabilitation costs by 12%\",\"+ Increases strength gym gains by 15%\",\"+ Increases speed gym gains by 10%\",\"+ Increases defense gym gains by 15%\",\"+ Increases dexterity gym gains by 10%\",\"+ Increases strength passively by 1%\",\"+ Increases speed passively by 1%\"],\"book_perks\":[\"+ Doubles candy effects for 31 days\"]}\n");

        // Act
        Perks result = UserMapper.ofPerks(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(result.getJobPerks())
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(List.of(
                        "+ Ability to revive",
                        "- 10% Education length"
                ));
        softly.assertThat(result.getPropertyPerks())
                .hasSize(4)
                .containsExactlyInAnyOrderElementsOf(List.of(
                        "+ Access to airstrip",
                        "+ 1% Life regeneration",
                        "+ 2% Damage",
                        "+ 2% Gym gains"
                ));
        softly.assertThat(result.getStockPerks())
                .hasSize(1)
                .containsExactlyInAnyOrderElementsOf(List.of(
                        "10% Bank Interest Bonus (TCB)"
                ));
        softly.assertThat(result.getMeritPerks())
                .hasSize(10)
                .containsExactlyInAnyOrderElementsOf(List.of(
                        "+ 10 Maximum nerve",
                        "+ 4% Critical hit rate",
                        "+ 200% Awareness",
                        "+ Bank interest rate",
                        "+ 30% Strength",
                        "+ 30% Speed",
                        "+ 30% Dexterity",
                        "+ 30% Defense",
                        "+ 45% Life",
                        "+ Rifle dmg/acc"
                ));
        softly.assertThat(result.getEducationPerks())
                .hasSize(55)
                .containsExactlyInAnyOrderElementsOf(List.of(
                        "+ 10% Japanese blade damage",
                        "+ 2% Melee damage",
                        "+ Ability to find medieval coins",
                        "+ Ability to find sculptures and Quran pages",
                        "+ Ability to find senet board and amulets",
                        "+ Museum unlocked",
                        "+ 1% Damage",
                        "+ 10% Neck hit damage",
                        "- 25% Opponent stealth",
                        "+ 3% Critical hit chance",
                        "+ Medical booster equipping",
                        "+ Pharmacy unlocked",
                        "+ 1% Strength gym gains",
                        "+ 1% Speed gym gains",
                        "+ 1% Defense gym gains",
                        "+ 1% Dexterity gym gains",
                        "+ 10% Temporary booster effectiveness",
                        "+ 1% Gym gains",
                        "+ Coding of simple virus unlocked",
                        "+ Coding of polymorphic and tunneling virus",
                        "+ Hacking unlocked",
                        "+ Coding of armored and stealth virus",
                        "+ 10% Hacking success rate",
                        "+ Anonymous mailing unlocked",
                        "+ 10% Awareness",
                        "+ 10% crime success rate",
                        "+ Kick attack unlocked",
                        "+ 100% Fist damage",
                        "+ 1.00 Machine gun accuracy",
                        "+ 1.00 SMG accuracy",
                        "+ 1.00 Pistol accuracy",
                        "+ 1.00 Rifle accuracy",
                        "+ 1.00 Heavy artillery accuracy",
                        "+ Weapon experience unlocked",
                        "+ Jail escape chance",
                        "+ Self bailing unlocked",
                        "- 5% Estate agent cost",
                        "- Escape jail nerve cost",
                        "+ Company advertising effectiveness",
                        "+ Driving crimes unlocked",
                        "+ 1.00 Temporary weapon accuracy",
                        "+ 5% Temporary weapon damage",
                        "+ 15% Hunting bonus",
                        "+ 1.00 Shotgun accuracy",
                        "+ Sports shop unlocked",
                        "+ Withdraw and deliver blood",
                        "+ 1% Company productivity",
                        "+ 5% Strength",
                        "+ 14% Speed",
                        "+ 19% Dexterity",
                        "+ 11% Defense",
                        "+ 20% Medical item effectiveness",
                        "- 30% Virus coding time",
                        "+ 65% Busting skill",
                        "- 57.25% Bail cost"
                ));
        softly.assertThat(result.getEnhancerPerks())
                .hasSize(18)
                .containsExactlyInAnyOrderElementsOf(List.of(
                        "+ Search for cash success rate (Pair of Glasses)",
                        "+ Bootlegging success rate (High-Speed DVD Drive)",
                        "+ Shoplifting success rate (Mountain Bike)",
                        "+ Pickpocketing success rate (Cut-Throat Razor)",
                        "+ Larceny success rate (Slim Crowbar)",
                        "+ Armed robbery success rate (Balaclava)",
                        "+ Transport drugs success rate (Advanced Driving Tactics)",
                        "+ Computer virus success rate (Ergonomic Keyboard)",
                        "+ Assassination success rate (Tracking Device)",
                        "+ Grand theft auto success rate (Screwdriver)",
                        "+ Pawn shop success rate (Fanny Pack)",
                        "+ Counterfeiting success rate (Tumble Dryer)",
                        "+ Kidnapping success rate (Chloroform)",
                        "+ Arms Trafficking success rate (Heavy Duty Padlock)",
                        "+ Bombing success rate (Duct Tape)",
                        "+ Hacking success rate (Wireless Dongle)",
                        "+ 10% Church praying bonus (Rosary Beads)",
                        "+ 4 Travel items (Large Suitcase)"
                ));
        softly.assertThat(result.getCompanyPerks())
                .hasSize(4)
                .containsExactlyInAnyOrderElementsOf(List.of(
                        "+ 50% decreased bail costs",
                        "+ Easier to bust more people at once",
                        "+ 20% organised crime skill",
                        "+ View success chance of potential busts"
                ));
        softly.assertThat(result.getFactionPerks())
                .hasSize(20)
                .containsExactlyInAnyOrderElementsOf(List.of(
                        "+ Increases maximum nerve by 40",
                        "+ Provides a 25% passive bonus to crime EXP",
                        "+ Decreases all jail time by 28%",
                        "+ Decreases bust nerve cost by 2",
                        "+ Increase bust success chance by 50%",
                        "+ Increases happy gain from candy by 50%",
                        "+ Increases energy gain from energy drinks by 50%",
                        "+ Adds 24 hours of maximum booster cooldown",
                        "+ Increases nerve gain from alcohol by 50%",
                        "+ Reduces the side effects of drugs and increases addiction mitigation by 30%",
                        "+ Decreases risk of overdose by 30%",
                        "+ Reduces drug addiction gain by 50%",
                        "+ Increases maximum traveling capacity by 10",
                        "+ Reduces rehabilitation costs by 12%",
                        "+ Increases strength gym gains by 15%",
                        "+ Increases speed gym gains by 10%",
                        "+ Increases defense gym gains by 15%",
                        "+ Increases dexterity gym gains by 10%",
                        "+ Increases strength passively by 1%",
                        "+ Increases speed passively by 1%"
                ));
        softly.assertThat(result.getBookPerks())
                .hasSize(1)
                .containsExactlyInAnyOrderElementsOf(List.of(
                        "+ Doubles candy effects for 31 days"
                ));

        softly.assertAll();
    }

    @Test
    void ofPersonalStats() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"personalstats\":{\"bazaarcustomers\":7801,\"bazaarsales\":2714831,\"bazaarprofit\":75932306550,\"useractivity\":22624363,\"activestreak\":459,\"bestactivestreak\":459,\"itemsbought\":1043,\"pointsbought\":29128,\"itemsboughtabroad\":20616,\"moneyinvested\":35689375237,\"investedprofit\":5497581175,\"weaponsbought\":4188,\"trades\":3815,\"itemssent\":589,\"auctionswon\":17,\"auctionsells\":2,\"pointssold\":252428,\"attackswon\":9728,\"attackslost\":817,\"attacksdraw\":143,\"bestkillstreak\":534,\"killstreak\":5,\"moneymugged\":660791618,\"attacksstealthed\":5528,\"attackhits\":36288,\"attackmisses\":12278,\"attackdamage\":28907918,\"attackcriticalhits\":5502,\"respectforfaction\":48138,\"onehitkills\":4122,\"defendswon\":1255,\"defendslost\":3505,\"defendsstalemated\":10,\"bestdamage\":9205,\"roundsfired\":79750,\"yourunaway\":1018,\"theyrunaway\":5,\"highestbeaten\":100,\"peoplebusted\":3150,\"failedbusts\":465,\"peoplebought\":500,\"peopleboughtspent\":41927499,\"virusescoded\":112,\"cityfinds\":614,\"traveltimes\":780,\"bountiesplaced\":139,\"bountiesreceived\":145,\"bountiescollected\":534,\"totalbountyreward\":79103973,\"revives\":2712,\"revivesreceived\":103,\"medicalitemsused\":16487,\"statenhancersused\":0,\"trainsreceived\":1044,\"totalbountyspent\":19955670,\"drugsused\":4065,\"overdosed\":67,\"meritsbought\":47,\"personalsplaced\":0,\"classifiedadsplaced\":1,\"mailssent\":1296,\"friendmailssent\":16,\"factionmailssent\":221,\"companymailssent\":4,\"spousemailssent\":1,\"largestmug\":37116538,\"cantaken\":861,\"exttaken\":66,\"kettaken\":50,\"lsdtaken\":51,\"opitaken\":50,\"shrtaken\":50,\"spetaken\":50,\"pcptaken\":50,\"xantaken\":2778,\"victaken\":59,\"chahits\":1459,\"heahits\":734,\"axehits\":1650,\"grehits\":322,\"machits\":133,\"pishits\":143,\"rifhits\":1331,\"shohits\":196,\"smghits\":347,\"piehits\":3141,\"slahits\":1055,\"argtravel\":50,\"mextravel\":133,\"dubtravel\":20,\"hawtravel\":51,\"japtravel\":23,\"lontravel\":47,\"soutravel\":56,\"switravel\":162,\"chitravel\":50,\"cantravel\":107,\"dumpfinds\":1715,\"dumpsearches\":1715,\"itemsdumped\":6192,\"daysbeendonator\":1299,\"caytravel\":81,\"jailed\":856,\"hospital\":7752,\"attacksassisted\":223,\"bloodwithdrawn\":7055,\"networth\":25073965359,\"missionscompleted\":1,\"contractscompleted\":702,\"dukecontractscompleted\":702,\"missioncreditsearned\":29220,\"consumablesused\":29623,\"candyused\":5411,\"alcoholused\":24183,\"energydrinkused\":29,\"nerverefills\":866,\"unarmoredwon\":372,\"h2hhits\":290,\"organisedcrimes\":281,\"territorytime\":2770783,\"territoryjoins\":2003,\"stockpayouts\":914,\"stockprofits\":954386795,\"stocklosses\":276160981,\"stockfees\":22475154,\"arrestsmade\":0,\"tokenrefills\":12,\"booksread\":7,\"traveltime\":8279449,\"boostersused\":125,\"rehabs\":1072,\"rehabcost\":260875000,\"awards\":442,\"receivedbountyvalue\":29982264,\"networthpending\":0,\"networthwallet\":0,\"networthbank\":2398000000,\"networthpoints\":626619319,\"networthcayman\":0,\"networthvault\":0,\"networthpiggybank\":3400000,\"networthitems\":2452155963,\"networthdisplaycase\":56891798,\"networthbazaar\":55006598,\"networthproperties\":2929182000,\"networthstockmarket\":16091454500,\"networthitemmarket\":0,\"networthauctionhouse\":0,\"networthcompany\":0,\"networthbookie\":445060388,\"networthenlistedcars\":16194793,\"networthloan\":0,\"networthunpaidfees\":0,\"racingskill\":18,\"raceswon\":753,\"racesentered\":3556,\"racingpointsearned\":3125,\"specialammoused\":15837,\"cityitemsbought\":22999,\"hollowammoused\":7080,\"tracerammoused\":3500,\"piercingammoused\":2750,\"incendiaryammoused\":2507,\"attackswonabroad\":167,\"defendslostabroad\":65,\"retals\":8,\"elo\":1729,\"strength\":69,\"defense\":69,\"speed\":69,\"dexterity\":69,\"totalstats\":276,\"manuallabor\":18325,\"intelligence\":71524,\"endurance\":121184,\"totalworkingstats\":211033,\"jobpointsused\":10437,\"reviveskill\":100,\"itemslooted\":3,\"refills\":1067}}");

        // Act
        PersonalStats result = UserMapper.ofPersonalStats(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(result.getBazaarCustomers()).isEqualTo(7801);
        softly.assertThat(result.getBazaarSales()).isEqualTo(2714831);
        softly.assertThat(result.getBazaarProfit()).isEqualTo(75932306550L);
        softly.assertThat(result.getUserActivity()).isEqualTo(
                Duration.ofDays(261)
                        .plusHours(20)
                        .plusMinutes(32)
                        .plusSeconds(43)
        );
        softly.assertThat(result.getActiveStreak()).isEqualTo(459);
        softly.assertThat(result.getBestActiveStreak()).isEqualTo(459);
        softly.assertThat(result.getItemsBought()).isEqualTo(1043);
        softly.assertThat(result.getPointsBought()).isEqualTo(29128);
        softly.assertThat(result.getItemsBoughtAbroad()).isEqualTo(20616);
        softly.assertThat(result.getMoneyInvested()).isEqualTo(35689375237L);
        softly.assertThat(result.getInvestedProfit()).isEqualTo(5497581175L);
        softly.assertThat(result.getWeaponsBought()).isEqualTo(4188);
        softly.assertThat(result.getTrades()).isEqualTo(3815);
        softly.assertThat(result.getItemsSent()).isEqualTo(589);
        softly.assertThat(result.getAuctionsWon()).isEqualTo(17);
        softly.assertThat(result.getAuctionSells()).isEqualTo(2);
        softly.assertThat(result.getPointsSold()).isEqualTo(252428);
        softly.assertThat(result.getAttacksWon()).isEqualTo(9728);
        softly.assertThat(result.getAttacksLost()).isEqualTo(817);
        softly.assertThat(result.getAttacksDraw()).isEqualTo(143);
        softly.assertThat(result.getBestKillStreak()).isEqualTo(534);
        softly.assertThat(result.getKillStreak()).isEqualTo(5);
        softly.assertThat(result.getMoneyMugged()).isEqualTo(660791618);
        softly.assertThat(result.getAttacksStealthed()).isEqualTo(5528);
        softly.assertThat(result.getAttackHits()).isEqualTo(36288);
        softly.assertThat(result.getAttackMisses()).isEqualTo(12278);
        softly.assertThat(result.getAttackDamage()).isEqualTo(28907918);
        softly.assertThat(result.getAttackCriticalHits()).isEqualTo(5502);
        softly.assertThat(result.getRespectForFaction()).isEqualTo(48138);
        softly.assertThat(result.getOneHitKills()).isEqualTo(4122);
        softly.assertThat(result.getDefendsWon()).isEqualTo(1255);
        softly.assertThat(result.getDefendsLost()).isEqualTo(3505);
        softly.assertThat(result.getDefendsStalemated()).isEqualTo(10);
        softly.assertThat(result.getBestDamage()).isEqualTo(9205);
        softly.assertThat(result.getRoundsFired()).isEqualTo(79750);
        softly.assertThat(result.getAttacksRunaway()).isEqualTo(1018);
        softly.assertThat(result.getDefendsRunaway()).isEqualTo(5);
        softly.assertThat(result.getHighestBeaten()).isEqualTo(100);
        softly.assertThat(result.getPeopleBusted()).isEqualTo(3150);
        softly.assertThat(result.getFailedBusts()).isEqualTo(465);
        softly.assertThat(result.getPeopleBought()).isEqualTo(500);
        softly.assertThat(result.getPeopleBoughtSpent()).isEqualTo(41927499);
        softly.assertThat(result.getVirusesCoded()).isEqualTo(112);
        softly.assertThat(result.getCityFinds()).isEqualTo(614);
        softly.assertThat(result.getTravelTimes()).isEqualTo(780);
        softly.assertThat(result.getBountiesPlaced()).isEqualTo(139);
        softly.assertThat(result.getBountiesReceived()).isEqualTo(145);
        softly.assertThat(result.getBountiesCollected()).isEqualTo(534);
        softly.assertThat(result.getTotalBountyReward()).isEqualTo(79103973);
        softly.assertThat(result.getRevives()).isEqualTo(2712);
        softly.assertThat(result.getRevivesReceived()).isEqualTo(103);
        softly.assertThat(result.getMedicalItemsUsed()).isEqualTo(16487);
        softly.assertThat(result.getStatEnhancersUsed()).isEqualTo(0);
        softly.assertThat(result.getTrainsReceived()).isEqualTo(1044);
        softly.assertThat(result.getTotalBountySpent()).isEqualTo(19955670);
        softly.assertThat(result.getDrugsUsed()).isEqualTo(4065);
        softly.assertThat(result.getOverdosed()).isEqualTo(67);
        softly.assertThat(result.getMeritsBought()).isEqualTo(47);
        softly.assertThat(result.getPersonalsPlaced()).isEqualTo(0);
        softly.assertThat(result.getClassifiedAdsPlaced()).isEqualTo(1);
        softly.assertThat(result.getMailsSent()).isEqualTo(1296);
        softly.assertThat(result.getFriendMailsSent()).isEqualTo(16);
        softly.assertThat(result.getFactionMailsSent()).isEqualTo(221);
        softly.assertThat(result.getCompanyMailsSent()).isEqualTo(4);
        softly.assertThat(result.getSpouseMailsSent()).isEqualTo(1);
        softly.assertThat(result.getLargestMug()).isEqualTo(37116538);
        softly.assertThat(result.getCannabisTaken()).isEqualTo(861);
        softly.assertThat(result.getEcstasyTaken()).isEqualTo(66);
        softly.assertThat(result.getKetamineTaken()).isEqualTo(50);
        softly.assertThat(result.getLsdTaken()).isEqualTo(51);
        softly.assertThat(result.getOpiumTaken()).isEqualTo(50);
        softly.assertThat(result.getShroomsTaken()).isEqualTo(50);
        softly.assertThat(result.getSpeedTaken()).isEqualTo(50);
        softly.assertThat(result.getPcpTaken()).isEqualTo(50);
        softly.assertThat(result.getXanaxTaken()).isEqualTo(2778);
        softly.assertThat(result.getVicodinTaken()).isEqualTo(59);
        softly.assertThat(result.getMechanicalHits()).isEqualTo(1459);
        softly.assertThat(result.getHeavyArtilleryHits()).isEqualTo(734);
        softly.assertThat(result.getClubbingHits()).isEqualTo(1650);
        softly.assertThat(result.getTemporaryHits()).isEqualTo(322);
        softly.assertThat(result.getMachineGunHits()).isEqualTo(133);
        softly.assertThat(result.getPistolHits()).isEqualTo(143);
        softly.assertThat(result.getRifleHits()).isEqualTo(1331);
        softly.assertThat(result.getShotgunHits()).isEqualTo(196);
        softly.assertThat(result.getSubMachineGunHits()).isEqualTo(347);
        softly.assertThat(result.getPiercingHits()).isEqualTo(3141);
        softly.assertThat(result.getSlashingHits()).isEqualTo(1055);
        softly.assertThat(result.getArgentinaTravel()).isEqualTo(50);
        softly.assertThat(result.getMexicoTravel()).isEqualTo(133);
        softly.assertThat(result.getUnitedArabEmiratesTravel()).isEqualTo(20);
        softly.assertThat(result.getHawaiiTravel()).isEqualTo(51);
        softly.assertThat(result.getJapanTravel()).isEqualTo(23);
        softly.assertThat(result.getUnitedKingdomTravel()).isEqualTo(47);
        softly.assertThat(result.getSouthAfricaTravel()).isEqualTo(56);
        softly.assertThat(result.getSwitzerlandTravel()).isEqualTo(162);
        softly.assertThat(result.getChinaTravel()).isEqualTo(50);
        softly.assertThat(result.getCanadaTravel()).isEqualTo(107);
        softly.assertThat(result.getDumpFinds()).isEqualTo(1715);
        softly.assertThat(result.getDumpSearches()).isEqualTo(1715);
        softly.assertThat(result.getItemsDumped()).isEqualTo(6192);
        softly.assertThat(result.getDaysBeenDonator()).isEqualTo(Duration.ofDays(1299));
        softly.assertThat(result.getCaymanIslandsTravel()).isEqualTo(81);
        softly.assertThat(result.getJailed()).isEqualTo(856);
        softly.assertThat(result.getHospital()).isEqualTo(7752);
        softly.assertThat(result.getAttacksAssisted()).isEqualTo(223);
        softly.assertThat(result.getBloodWithdrawn()).isEqualTo(7055);
        softly.assertThat(result.getNetworth()).isEqualTo(25073965359L);
        softly.assertThat(result.getMissionsCompleted()).isEqualTo(1);
        softly.assertThat(result.getContractsCompleted()).isEqualTo(702);
        softly.assertThat(result.getDukeContractsCompleted()).isEqualTo(702);
        softly.assertThat(result.getMissionCreditsEarned()).isEqualTo(29220);
        softly.assertThat(result.getConsumablesUsed()).isEqualTo(29623);
        softly.assertThat(result.getCandyUsed()).isEqualTo(5411);
        softly.assertThat(result.getAlcoholUsed()).isEqualTo(24183);
        softly.assertThat(result.getEnergyDrinkUsed()).isEqualTo(29);
        softly.assertThat(result.getNerveRefills()).isEqualTo(866);
        softly.assertThat(result.getUnarmoredWon()).isEqualTo(372);
        softly.assertThat(result.getHandToHandHits()).isEqualTo(290);
        softly.assertThat(result.getOrganisedCrimes()).isEqualTo(281);
        softly.assertThat(result.getTerritoryTime()).isEqualTo(
                Duration.ofDays(32)
                        .plusHours(1)
                        .plusMinutes(39)
                        .plusSeconds(43)
        );
        softly.assertThat(result.getTerritoryJoins()).isEqualTo(2003);
        softly.assertThat(result.getStockPayouts()).isEqualTo(914);
        softly.assertThat(result.getStockProfits()).isEqualTo(954386795);
        softly.assertThat(result.getStockLosses()).isEqualTo(276160981);
        softly.assertThat(result.getStockFees()).isEqualTo(22475154);
        softly.assertThat(result.getArrestsMade()).isEqualTo(0);
        softly.assertThat(result.getTokenRefills()).isEqualTo(12);
        softly.assertThat(result.getBooksRead()).isEqualTo(7);
        softly.assertThat(result.getTravelTime()).isEqualTo(
                Duration.ofDays(95)
                        .plusHours(19)
                        .plusMinutes(50)
                        .plusSeconds(49)
        );
        softly.assertThat(result.getBoostersUsed()).isEqualTo(125);
        softly.assertThat(result.getRehabs()).isEqualTo(1072);
        softly.assertThat(result.getRehabCost()).isEqualTo(260875000);
        softly.assertThat(result.getAwards()).isEqualTo(442);
        softly.assertThat(result.getReceivedBountyValue()).isEqualTo(29982264);
        softly.assertThat(result.getNetworthPending()).isEqualTo(0);
        softly.assertThat(result.getNetworthWallet()).isEqualTo(0);
        softly.assertThat(result.getNetworthBank()).isEqualTo(2398000000L);
        softly.assertThat(result.getNetworthPoints()).isEqualTo(626619319);
        softly.assertThat(result.getNetworthCayman()).isEqualTo(0);
        softly.assertThat(result.getNetworthVault()).isEqualTo(0);
        softly.assertThat(result.getNetworthPiggyBank()).isEqualTo(3400000);
        softly.assertThat(result.getNetworthItems()).isEqualTo(2452155963L);
        softly.assertThat(result.getNetworthDisplayCase()).isEqualTo(56891798);
        softly.assertThat(result.getNetworthBazaar()).isEqualTo(55006598);
        softly.assertThat(result.getNetworthProperties()).isEqualTo(2929182000L);
        softly.assertThat(result.getNetworthStockMarket()).isEqualTo(16091454500L);
        softly.assertThat(result.getNetworthItemMarket()).isEqualTo(0);
        softly.assertThat(result.getNetworthAuctionHouse()).isEqualTo(0);
        softly.assertThat(result.getNetworthCompany()).isEqualTo(0);
        softly.assertThat(result.getNetworthBookie()).isEqualTo(445060388);
        softly.assertThat(result.getNetworthenListedCars()).isEqualTo(16194793);
        softly.assertThat(result.getNetworthLoan()).isEqualTo(0);
        softly.assertThat(result.getNetworthUnpaidFees()).isEqualTo(0);
        softly.assertThat(result.getRacingSkill()).isEqualTo(18);
        softly.assertThat(result.getRacesWon()).isEqualTo(753);
        softly.assertThat(result.getRacesEntered()).isEqualTo(3556);
        softly.assertThat(result.getRacingPointsEarned()).isEqualTo(3125);
        softly.assertThat(result.getSpecialAmmoUsed()).isEqualTo(15837);
        softly.assertThat(result.getCityItemsBought()).isEqualTo(22999);
        softly.assertThat(result.getHollowAmmoUsed()).isEqualTo(7080);
        softly.assertThat(result.getTracerAmmoUsed()).isEqualTo(3500);
        softly.assertThat(result.getPiercingAmmoUsed()).isEqualTo(2750);
        softly.assertThat(result.getIncendiaryAmmoUsed()).isEqualTo(2507);
        softly.assertThat(result.getAttacksWonAbroad()).isEqualTo(167);
        softly.assertThat(result.getDefendsLostAbroad()).isEqualTo(65);
        softly.assertThat(result.getRetals()).isEqualTo(8);
        softly.assertThat(result.getElo()).isEqualTo(1729);
        softly.assertThat(result.getStrength()).isEqualTo(69);
        softly.assertThat(result.getDefense()).isEqualTo(69);
        softly.assertThat(result.getSpeed()).isEqualTo(69);
        softly.assertThat(result.getDexterity()).isEqualTo(69);
        softly.assertThat(result.getTotalStats()).isEqualTo(276);
        softly.assertThat(result.getManualLabor()).isEqualTo(18325);
        softly.assertThat(result.getIntelligence()).isEqualTo(71524);
        softly.assertThat(result.getEndurance()).isEqualTo(121184);
        softly.assertThat(result.getTotalWorkingStats()).isEqualTo(211033);
        softly.assertThat(result.getJobPointsUsed()).isEqualTo(10437);
        softly.assertThat(result.getReviveSkill()).isEqualTo(100);
        softly.assertThat(result.getItemsLooted()).isEqualTo(3);
        softly.assertThat(result.getEnergyRefills()).isEqualTo(1067);

        softly.assertAll();
    }

    @Test
    void ofProfile() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"rank\":\"Heroic Trader\",\"level\":94,\"gender\":\"Male\",\"property\":\"Private Island\",\"signup\":\"2018-04-18 16:26:12\",\"awards\":442,\"friends\":251,\"enemies\":31,\"forum_posts\":2681,\"karma\":3614,\"age\":1311,\"role\":\"Committee\",\"donator\":1,\"player_id\":2114440,\"name\":\"DeKleineKobini\",\"property_id\":3150854,\"competition\":null,\"life\":{\"current\":6633,\"maximum\":6633,\"increment\":397,\"interval\":300,\"ticktime\":278,\"fulltime\":0},\"status\":{\"description\":\"Okay\",\"details\":\"\",\"state\":\"Okay\",\"color\":\"green\",\"until\":0},\"job\":{\"position\":\"Employee\",\"company_id\":86818,\"company_name\":\"Jux @ Law\",\"company_type\":2},\"faction\":{\"position\":\"Thunderbird\",\"faction_id\":33007,\"days_in_faction\":17,\"faction_name\":\"HAKA\",\"faction_tag\":\"HAKA\"},\"married\":{\"spouse_id\":2122347,\"spouse_name\":\"ExKioz\",\"duration\":1280},\"basicicons\":{\"icon6\":\"Male\",\"icon4\":\"Subscriber\",\"icon10\":\"Player Committee - Member\",\"icon8\":\"Married - To ExKioz\",\"icon27\":\"Company - Attorney of Jux @ Law (Law Firm)\",\"icon9\":\"Faction - Thunderbird of HAKA\",\"icon35\":\"Bazaar - This person has items in their bazaar for sale\"},\"states\":{\"hospital_timestamp\":0,\"jail_timestamp\":0},\"last_action\":{\"status\":\"Idle\",\"timestamp\":1637362180,\"relative\":\"1 hour ago\"}}");

        // Act
        Profile profile = UserMapper.ofProfile(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(profile.getRank()).isEqualTo("Heroic Trader");
        softly.assertThat(profile.getLevel()).isEqualTo(94);
        softly.assertThat(profile.getGender()).isEqualTo(Gender.MALE);
        softly.assertThat(profile.getProperty()).isEqualTo("Private Island");
        softly.assertThat(profile.getSignup()).isEqualTo(LocalDateTime.of(2018, 4, 18, 16, 26, 12));
        softly.assertThat(profile.getAwards()).isEqualTo(442);
        softly.assertThat(profile.getFriends()).isEqualTo(251);
        softly.assertThat(profile.getEnemies()).isEqualTo(31);
        softly.assertThat(profile.getForumPosts()).isEqualTo(2681);
        softly.assertThat(profile.getKarma()).isEqualTo(3614);
        softly.assertThat(profile.getAge()).isEqualTo(1311);
        softly.assertThat(profile.getRole()).isEqualTo("Committee");
        softly.assertThat(profile.isDonator()).isEqualTo(true);
        softly.assertThat(profile.getPlayerId()).isEqualTo(2114440);
        softly.assertThat(profile.getName()).isEqualTo("DeKleineKobini");
        softly.assertThat(profile.getPropertyId()).isEqualTo(3150854);

        softly.assertThat(profile.getLife()).isNotNull();
        softly.assertThat(profile.getLife().getCurrent()).isEqualTo(6633);
        softly.assertThat(profile.getLife().getMaximum()).isEqualTo(6633);
        softly.assertThat(profile.getLife().getIncrement()).isEqualTo(397);
        softly.assertThat(profile.getLife().getInterval()).hasMinutes(5);
        softly.assertThat(profile.getLife().getTicktime()).hasSeconds(278);
        softly.assertThat(profile.getLife().getFulltime()).hasSeconds(0);

        softly.assertThat(profile.getStatus()).isNotNull();
        softly.assertThat(profile.getStatus().getDescription()).isEqualTo("Okay");
        softly.assertThat(profile.getStatus().getDetails()).isEqualTo("");
        softly.assertThat(profile.getStatus().getState()).isEqualTo(Status.State.OKAY);
        softly.assertThat(profile.getStatus().getColor()).isEqualTo("green");
        softly.assertThat(profile.getStatus().getUntil()).isEqualTo(Instant.ofEpochSecond(0).atOffset(ZoneOffset.UTC).toLocalDateTime());

        softly.assertThat(profile.getJob()).isNotNull();
        softly.assertThat(profile.getJob().getPosition()).isEqualTo("Employee");
        softly.assertThat(profile.getJob().getCompanyId()).isEqualTo(86818);
        softly.assertThat(profile.getJob().getCompanyName()).isEqualTo("Jux @ Law");
        softly.assertThat(profile.getJob().getCompanyType()).isEqualTo(2);

        softly.assertThat(profile.getFaction()).isNotNull();
        softly.assertThat(profile.getFaction().getPosition()).isEqualTo("Thunderbird");
        softly.assertThat(profile.getFaction().getFactionId()).isEqualTo(33007);
        softly.assertThat(profile.getFaction().getDaysInFaction()).hasDays(17);
        softly.assertThat(profile.getFaction().getFactionName()).isEqualTo("HAKA");
        softly.assertThat(profile.getFaction().getFactionTag()).isEqualTo("HAKA");

        softly.assertThat(profile.getMarriage()).isNotNull();
        softly.assertThat(profile.getMarriage().getSpouseId()).isEqualTo(2122347);
        softly.assertThat(profile.getMarriage().getSpouseName()).isEqualTo("ExKioz");
        softly.assertThat(profile.getMarriage().getDuration()).hasDays(1280);

        softly.assertThat(profile.getBasicIcons())
                .isNotNull()
                .contains(
                        entry("icon6", "Male"),
                        entry("icon4", "Subscriber"),
                        entry("icon10", "Player Committee - Member"),
                        entry("icon8", "Married - To ExKioz"),
                        entry("icon27", "Company - Attorney of Jux @ Law (Law Firm)"),
                        entry("icon9", "Faction - Thunderbird of HAKA"),
                        entry("icon35", "Bazaar - This person has items in their bazaar for sale")
                );

        softly.assertThat(profile.getStates()).isNotNull();
        softly.assertThat(profile.getStates().getHospitalTimestamp()).isEqualTo(0);
        softly.assertThat(profile.getStates().getJailTimestamp()).isEqualTo(0);

        softly.assertThat(profile.getLastAction()).isNotNull();
        softly.assertThat(profile.getLastAction().getStatus()).isEqualTo(LastActionStatus.IDLE);
        softly.assertThat(profile.getLastAction().getTimestamp()).isEqualTo(LocalDateTime.of(2021, 11, 19, 22, 49, 40));
        softly.assertThat(profile.getLastAction().getRelative()).isEqualTo("1 hour ago");

        softly.assertAll();
    }

    @Test
    void ofProfile_Jailed() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"rank\":\"Heroic Trader\",\"level\":94,\"gender\":\"Male\",\"property\":\"Private Island\",\"signup\":\"2018-04-18 16:26:12\",\"awards\":442,\"friends\":251,\"enemies\":31,\"forum_posts\":2681,\"karma\":3614,\"age\":1311,\"role\":\"Committee\",\"donator\":1,\"player_id\":2114440,\"name\":\"DeKleineKobini\",\"property_id\":3150854,\"competition\":null,\"life\":{\"current\":6633,\"maximum\":6633,\"increment\":397,\"interval\":300,\"ticktime\":278,\"fulltime\":0},\"status\":{\"description\":\"In jail for 2 hrs 5 mins \",\"details\":\"Was caught trying to break out <a class='t-blue' href = http://www.torn.com/profiles.php?XID=1043531>Warrior</a>.\",\"state\":\"Jail\",\"color\":\"red\",\"until\":1637446695},\"job\":{\"position\":\"Employee\",\"company_id\":86818,\"company_name\":\"Jux @ Law\",\"company_type\":2},\"faction\":{\"position\":\"Thunderbird\",\"faction_id\":33007,\"days_in_faction\":17,\"faction_name\":\"HAKA\",\"faction_tag\":\"HAKA\"},\"married\":{\"spouse_id\":2122347,\"spouse_name\":\"ExKioz\",\"duration\":1280},\"basicicons\":{\"icon6\":\"Male\",\"icon4\":\"Subscriber\",\"icon10\":\"Player Committee - Member\",\"icon8\":\"Married - To ExKioz\",\"icon27\":\"Company - Attorney of Jux @ Law (Law Firm)\",\"icon9\":\"Faction - Thunderbird of HAKA\",\"icon35\":\"Bazaar - This person has items in their bazaar for sale\"},\"states\":{\"hospital_timestamp\":0,\"jail_timestamp\":0},\"last_action\":{\"status\":\"Idle\",\"timestamp\":1637362180,\"relative\":\"1 hour ago\"}}");

        // Act
        Profile profile = UserMapper.ofProfile(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(profile.getRank()).isEqualTo("Heroic Trader");
        softly.assertThat(profile.getLevel()).isEqualTo(94);
        softly.assertThat(profile.getGender()).isEqualTo(Gender.MALE);
        softly.assertThat(profile.getProperty()).isEqualTo("Private Island");
        softly.assertThat(profile.getSignup()).isEqualTo(LocalDateTime.of(2018, 4, 18, 16, 26, 12));
        softly.assertThat(profile.getAwards()).isEqualTo(442);
        softly.assertThat(profile.getFriends()).isEqualTo(251);
        softly.assertThat(profile.getEnemies()).isEqualTo(31);
        softly.assertThat(profile.getForumPosts()).isEqualTo(2681);
        softly.assertThat(profile.getKarma()).isEqualTo(3614);
        softly.assertThat(profile.getAge()).isEqualTo(1311);
        softly.assertThat(profile.getRole()).isEqualTo("Committee");
        softly.assertThat(profile.isDonator()).isEqualTo(true);
        softly.assertThat(profile.getPlayerId()).isEqualTo(2114440);
        softly.assertThat(profile.getName()).isEqualTo("DeKleineKobini");
        softly.assertThat(profile.getPropertyId()).isEqualTo(3150854);

        softly.assertThat(profile.getLife()).isNotNull();
        softly.assertThat(profile.getLife().getCurrent()).isEqualTo(6633);
        softly.assertThat(profile.getLife().getMaximum()).isEqualTo(6633);
        softly.assertThat(profile.getLife().getIncrement()).isEqualTo(397);
        softly.assertThat(profile.getLife().getInterval()).hasMinutes(5);
        softly.assertThat(profile.getLife().getTicktime()).hasSeconds(278);
        softly.assertThat(profile.getLife().getFulltime()).hasSeconds(0);

        softly.assertThat(profile.getStatus()).isNotNull();
        softly.assertThat(profile.getStatus().getDescription()).isEqualTo("In jail for 2 hrs 5 mins ");
        softly.assertThat(profile.getStatus().getDetails()).isEqualTo("Was caught trying to break out <a class='t-blue' href = http://www.torn.com/profiles.php?XID=1043531>Warrior</a>.");
        softly.assertThat(profile.getStatus().getState()).isEqualTo(Status.State.JAIL);
        softly.assertThat(profile.getStatus().getColor()).isEqualTo("red");
        softly.assertThat(profile.getStatus().getUntil()).isEqualTo(LocalDateTime.of(2021, 11, 20, 22, 18, 15));

        softly.assertThat(profile.getJob()).isNotNull();
        softly.assertThat(profile.getJob().getPosition()).isEqualTo("Employee");
        softly.assertThat(profile.getJob().getCompanyId()).isEqualTo(86818);
        softly.assertThat(profile.getJob().getCompanyName()).isEqualTo("Jux @ Law");
        softly.assertThat(profile.getJob().getCompanyType()).isEqualTo(2);

        softly.assertThat(profile.getFaction()).isNotNull();
        softly.assertThat(profile.getFaction().getPosition()).isEqualTo("Thunderbird");
        softly.assertThat(profile.getFaction().getFactionId()).isEqualTo(33007);
        softly.assertThat(profile.getFaction().getDaysInFaction()).hasDays(17);
        softly.assertThat(profile.getFaction().getFactionName()).isEqualTo("HAKA");
        softly.assertThat(profile.getFaction().getFactionTag()).isEqualTo("HAKA");

        softly.assertThat(profile.getMarriage()).isNotNull();
        softly.assertThat(profile.getMarriage().getSpouseId()).isEqualTo(2122347);
        softly.assertThat(profile.getMarriage().getSpouseName()).isEqualTo("ExKioz");
        softly.assertThat(profile.getMarriage().getDuration()).hasDays(1280);

        softly.assertThat(profile.getBasicIcons())
                .isNotNull()
                .contains(
                        entry("icon6", "Male"),
                        entry("icon4", "Subscriber"),
                        entry("icon10", "Player Committee - Member"),
                        entry("icon8", "Married - To ExKioz"),
                        entry("icon27", "Company - Attorney of Jux @ Law (Law Firm)"),
                        entry("icon9", "Faction - Thunderbird of HAKA"),
                        entry("icon35", "Bazaar - This person has items in their bazaar for sale")
                );

        softly.assertThat(profile.getStates()).isNotNull();
        softly.assertThat(profile.getStates().getHospitalTimestamp()).isEqualTo(0);
        softly.assertThat(profile.getStates().getJailTimestamp()).isEqualTo(0);

        softly.assertThat(profile.getLastAction()).isNotNull();
        softly.assertThat(profile.getLastAction().getStatus()).isEqualTo(LastActionStatus.IDLE);
        softly.assertThat(profile.getLastAction().getTimestamp()).isEqualTo(LocalDateTime.of(2021, 11, 19, 22, 49, 40));
        softly.assertThat(profile.getLastAction().getRelative()).isEqualTo("1 hour ago");

        softly.assertAll();
    }

    @Test
    void ofProperties() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"properties\":{\"2102352\":{\"owner_id\":2122347,\"property_type\":11,\"property\":\"Palace\",\"status\":\"Owned by their spouse\",\"happy\":1125,\"upkeep\":3000,\"staff_cost\":0,\"cost\":65000000,\"marketprice\":280000000,\"modifications\":{\"interior\":0,\"hot_tub\":0,\"sauna\":0,\"pool\":0,\"open_bar\":0,\"shooting_range\":0,\"vault\":4,\"medical_facility\":0,\"airstrip\":0,\"yacht\":0},\"staff\":{\"maid\":0,\"guard\":0,\"pilot\":0,\"butler\":0,\"doctor\":0}},\"3150854\":{\"owner_id\":2122347,\"property_type\":13,\"property\":\"Private Island\",\"status\":\"Owned by their spouse\",\"happy\":4525,\"upkeep\":100000,\"staff_cost\":252500,\"cost\":500000000,\"marketprice\":1057788000,\"modifications\":{\"interior\":2,\"hot_tub\":1,\"sauna\":1,\"pool\":3,\"open_bar\":1,\"shooting_range\":1,\"vault\":4,\"medical_facility\":1,\"airstrip\":1,\"yacht\":0},\"staff\":{\"maid\":4,\"guard\":5,\"pilot\":1,\"butler\":3,\"doctor\":1}},\"3393900\":{\"owner_id\":2114440,\"property_type\":13,\"property\":\"Private Island\",\"status\":\"Owned by them\",\"happy\":5025,\"upkeep\":100000,\"staff_cost\":252500,\"cost\":500000000,\"marketprice\":1952788000,\"modifications\":{\"interior\":2,\"hot_tub\":1,\"sauna\":1,\"pool\":3,\"open_bar\":1,\"shooting_range\":1,\"vault\":4,\"medical_facility\":1,\"airstrip\":1,\"yacht\":1},\"staff\":{\"maid\":4,\"guard\":5,\"pilot\":1,\"butler\":3,\"doctor\":1}},\"3511064\":{\"owner_id\":2122347,\"property_type\":7,\"property\":\"Villa\",\"status\":\"Owned by their spouse\",\"happy\":400,\"upkeep\":350,\"staff_cost\":0,\"cost\":1250000,\"marketprice\":1250000,\"modifications\":{\"interior\":0,\"hot_tub\":0,\"sauna\":0,\"pool\":0,\"open_bar\":0,\"shooting_range\":0,\"vault\":0,\"medical_facility\":0,\"airstrip\":0,\"yacht\":0},\"staff\":{\"maid\":0,\"guard\":0,\"pilot\":0,\"butler\":0,\"doctor\":0}},\"3553122\":{\"owner_id\":2122347,\"property_type\":4,\"property\":\"Detached House\",\"status\":\"Owned by their spouse\",\"happy\":200,\"upkeep\":150,\"staff_cost\":0,\"cost\":300000,\"marketprice\":300000,\"modifications\":{\"interior\":0,\"hot_tub\":0,\"sauna\":0,\"pool\":0,\"open_bar\":0,\"shooting_range\":0,\"vault\":0,\"medical_facility\":0,\"airstrip\":0,\"yacht\":0},\"staff\":{\"maid\":0,\"guard\":0,\"pilot\":0,\"butler\":0,\"doctor\":0}},\"3555137\":{\"owner_id\":2122347,\"property_type\":7,\"property\":\"Villa\",\"status\":\"Owned by their spouse\",\"happy\":400,\"upkeep\":350,\"staff_cost\":0,\"cost\":1250000,\"marketprice\":1250000,\"modifications\":{\"interior\":0,\"hot_tub\":0,\"sauna\":0,\"pool\":0,\"open_bar\":0,\"shooting_range\":0,\"vault\":0,\"medical_facility\":0,\"airstrip\":0,\"yacht\":0},\"staff\":{\"maid\":0,\"guard\":0,\"pilot\":0,\"butler\":0,\"doctor\":0}},\"3579493\":{\"owner_id\":2122347,\"property_type\":7,\"property\":\"Villa\",\"status\":\"Owned by their spouse\",\"happy\":400,\"upkeep\":350,\"staff_cost\":0,\"cost\":1250000,\"marketprice\":1250000,\"modifications\":{\"interior\":0,\"hot_tub\":0,\"sauna\":0,\"pool\":0,\"open_bar\":0,\"shooting_range\":0,\"vault\":0,\"medical_facility\":0,\"airstrip\":0,\"yacht\":0},\"staff\":{\"maid\":0,\"guard\":0,\"pilot\":0,\"butler\":0,\"doctor\":0}},\"3598509\":{\"owner_id\":2122347,\"property_type\":3,\"property\":\"Semi-Detached House\",\"status\":\"Owned by their spouse\",\"happy\":150,\"upkeep\":70,\"staff_cost\":0,\"cost\":75000,\"marketprice\":75000,\"modifications\":{\"interior\":0,\"hot_tub\":0,\"sauna\":0,\"pool\":0,\"open_bar\":0,\"shooting_range\":0,\"vault\":0,\"medical_facility\":0,\"airstrip\":0,\"yacht\":0},\"staff\":{\"maid\":0,\"guard\":0,\"pilot\":0,\"butler\":0,\"doctor\":0}},\"3739842\":{\"owner_id\":2114440,\"property_type\":13,\"property\":\"Private Island\",\"status\":\"Owned by them\",\"happy\":4275,\"upkeep\":100000,\"staff_cost\":30000,\"cost\":500000000,\"marketprice\":1952788000,\"modifications\":{\"interior\":2,\"hot_tub\":1,\"sauna\":1,\"pool\":3,\"open_bar\":1,\"shooting_range\":1,\"vault\":4,\"medical_facility\":1,\"airstrip\":1,\"yacht\":1},\"staff\":{\"maid\":0,\"guard\":0,\"pilot\":1,\"butler\":0,\"doctor\":0}},\"3849560\":{\"owner_id\":2122347,\"property_type\":4,\"property\":\"Detached House\",\"status\":\"Owned by their spouse\",\"happy\":200,\"upkeep\":150,\"staff_cost\":0,\"cost\":300000,\"marketprice\":300000,\"modifications\":{\"interior\":0,\"hot_tub\":0,\"sauna\":0,\"pool\":0,\"open_bar\":0,\"shooting_range\":0,\"vault\":0,\"medical_facility\":0,\"airstrip\":0,\"yacht\":0},\"staff\":{\"maid\":0,\"guard\":0,\"pilot\":0,\"butler\":0,\"doctor\":0}},\"3854091\":{\"owner_id\":2122347,\"property_type\":4,\"property\":\"Detached House\",\"status\":\"Owned by their spouse\",\"happy\":200,\"upkeep\":150,\"staff_cost\":0,\"cost\":300000,\"marketprice\":300000,\"modifications\":{\"interior\":0,\"hot_tub\":0,\"sauna\":0,\"pool\":0,\"open_bar\":0,\"shooting_range\":0,\"vault\":0,\"medical_facility\":0,\"airstrip\":0,\"yacht\":0},\"staff\":{\"maid\":0,\"guard\":0,\"pilot\":0,\"butler\":0,\"doctor\":0}}}}");

        // Act
        Map<Long, Properties> result = UserMapper.ofProperties(json);

        // Assert
        Properties.Modifications property1Modifications = new Properties.Modifications();
        property1Modifications.setInterior(2);
        property1Modifications.setHotTub(true);
        property1Modifications.setSauna(true);
        property1Modifications.setPool(3);
        property1Modifications.setOpenBar(true);
        property1Modifications.setShootingRange(true);
        property1Modifications.setVault(4);
        property1Modifications.setMedicalFacility(true);
        property1Modifications.setAirstrip(true);
        property1Modifications.setYacht(false);

        Properties.Staff property1Staff = new Properties.Staff();
        property1Staff.setMaid(4);
        property1Staff.setGuard(5);
        property1Staff.setPilot(true);
        property1Staff.setButler(3);
        property1Staff.setDoctor(true);

        Properties property1 = new Properties();
        property1.setOwnerId(2122347);
        property1.setPropertyType(13);
        property1.setProperty("Private Island");
        property1.setStatus("Owned by their spouse");
        property1.setHappy(4525);
        property1.setUpkeep(100000);
        property1.setStaffCost(252500);
        property1.setCost(500000000);
        property1.setMarketPrice(1057788000);
        property1.setModifications(property1Modifications);
        property1.setStaff(property1Staff);

        assertThat(result)
                .hasSize(11)
                .containsEntry(3150854L, property1);
    }

    @Test
    void ofReceivedEvents() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"receivedevents\":{\"1979908\":{\"owner\":1013799,\"timestamp\":1575493176,\"event\":\"You pop a few Xanax pills into your mouth and down a glass of water. A headache is followed by nausea and vomiting. You must have overdosed!\"},\"1979909\":{\"owner\":147761,\"timestamp\":1578281204,\"event\":\"You pop a few Xanax pills into your mouth and down a glass of water. A headache is followed by nausea and vomiting. You must have overdosed!\"},\"2142258\":{\"owner\":2069481,\"timestamp\":1595901942,\"event\":\"You take some Xanax and down a glass of water. A headache is followed by nausea and vomiting. You must have overdosed!\"},\"2142263\":{\"owner\":2069481,\"timestamp\":1595966824,\"event\":\"<a href = http://www.torn.com/profiles.php?XID=2068198>slabsaldinho</a> bought 30 x Crocus from your bazaar for $270,000.\"}}}");

        // Act
        Map<Long, ReceivedEvents> result = UserMapper.ofReceivedEvents(json);

        // Assert
        ReceivedEvents event1 = new ReceivedEvents();
        event1.setOwner(1013799);
        event1.setTimestamp(LocalDateTime.of(2019, 12, 4, 20, 59, 36));
        event1.setEvent("You pop a few Xanax pills into your mouth and down a glass of water. A headache is followed by nausea and vomiting. You must have overdosed!");

        ReceivedEvents event2 = new ReceivedEvents();
        event2.setOwner(2069481);
        event2.setTimestamp(LocalDateTime.of(2020, 7, 28, 20, 7, 4));
        event2.setEvent("<a href = http://www.torn.com/profiles.php?XID=2068198>slabsaldinho</a> bought 30 x Crocus from your bazaar for $270,000.");

        assertThat(result)
                .hasSize(4)
                .containsEntry(1979908L, event1)
                .containsEntry(2142263L, event2);
    }

    @Test
    void ofRefills() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"refills\":{\"energy_refill_used\":true,\"nerve_refill_used\":false,\"token_refill_used\":false,\"special_refills_available\":0}}");

        // Act
        Refills result = UserMapper.ofRefills(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(result.isEnergyRefillUsed()).isEqualTo(true);
        softly.assertThat(result.isNerveRefillUsed()).isEqualTo(false);
        softly.assertThat(result.isTokenRefillUsed()).isEqualTo(false);
        softly.assertThat(result.getSpecialRefillsAvailable()).isEqualTo(0);

        softly.assertAll();
    }

    @Test
    void ofReports() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"reports\":[{\"id\":\"619bc6a27e9adc6ad73b6bc1\",\"user_id\":2114440,\"target\":2616736,\"type\":\"stats\",\"report\":{\"strength\":10095484,\"speed\":10022851,\"dexterity\":4828144,\"defense\":13230202,\"total_battlestats\":38176681},\"timestamp\":1637598882},{\"id\":\"619a5cfbfbc2f7149b676362\",\"user_id\":2114440,\"target\":1906030,\"type\":\"stats\",\"report\":{\"strength\":801739451,\"dexterity\":6410,\"defense\":112339226,\"total_battlestats\":914088683},\"timestamp\":1637506299},{\"id\":\"619a5cf651f6f66e13534d3e\",\"user_id\":2114440,\"target\":1906030,\"type\":\"stats\",\"report\":{\"dexterity\":6410},\"timestamp\":1637506294},{\"id\":\"619a5ca1bd7c444cdf4942f1\",\"user_id\":2114440,\"target\":845247,\"type\":\"stats\",\"report\":{\"strength\":100348,\"speed\":28069,\"dexterity\":28345,\"defense\":29161,\"total_battlestats\":185923},\"timestamp\":1637506209},{\"id\":\"619a5c9d6834363cb643f9f5\",\"user_id\":2114440,\"target\":845247,\"type\":\"stats\",\"report\":{\"strength\":100348,\"total_battlestats\":185923},\"timestamp\":1637506205},{\"id\":\"6192845a5b4eda71722660a4\",\"user_id\":2114440,\"target\":2148162,\"type\":\"stats\",\"report\":{\"dexterity\":459515400,\"defense\":594763206,\"total_battlestats\":1867846158},\"timestamp\":1636992090},{\"id\":\"61928456a426820701050adf\",\"user_id\":2114440,\"target\":2148162,\"type\":\"stats\",\"report\":{\"strength\":412891175,\"dexterity\":459515400},\"timestamp\":1636992086},{\"id\":\"619284517bdcc22f1c5e483b\",\"user_id\":2114440,\"target\":2148162,\"type\":\"stats\",\"report\":{\"strength\":412891175,\"dexterity\":459515400},\"timestamp\":1636992081},{\"id\":\"61926f50005cc429037cf1f9\",\"user_id\":2114440,\"target\":1874664,\"type\":\"stats\",\"report\":{\"strength\":298739760,\"speed\":265305414,\"dexterity\":300173200,\"defense\":821099328,\"total_battlestats\":1685317702},\"timestamp\":1636986704},{\"id\":\"61926f4b9f935c27d131982c\",\"user_id\":2114440,\"target\":1874664,\"type\":\"stats\",\"report\":{\"strength\":298739760,\"speed\":265305414,\"dexterity\":300173200},\"timestamp\":1636986699},{\"id\":\"61926d7c07b87b472d03663f\",\"user_id\":2114440,\"target\":120362,\"type\":\"stats\",\"report\":{\"speed\":3512019454,\"total_battlestats\":11559403371},\"timestamp\":1636986236},{\"id\":\"618d5d74f6a731760738fc9d\",\"user_id\":2114440,\"target\":2134338,\"type\":\"stats\",\"report\":{\"strength\":500631248,\"speed\":701092556,\"dexterity\":505618351,\"defense\":659928826,\"total_battlestats\":2367270981},\"timestamp\":1636654452},{\"id\":\"618d07b18adf0b71db00dd22\",\"user_id\":2114440,\"target\":279361,\"type\":\"stats\",\"report\":{\"strength\":735034395,\"dexterity\":735965511,\"defense\":735493557,\"total_battlestats\":2943149659},\"timestamp\":1636632497},{\"id\":\"618d074e144bea637f73f46c\",\"user_id\":2114440,\"target\":443688,\"type\":\"stats\",\"report\":{\"strength\":537214725,\"speed\":343973904,\"dexterity\":810461471,\"total_battlestats\":2047708930},\"timestamp\":1636632398},{\"id\":\"618b9b8bcd8e961141516149\",\"user_id\":2114440,\"target\":1778268,\"type\":\"stats\",\"report\":{\"strength\":1548777888,\"speed\":1567770123,\"defense\":1556342777,\"total_battlestats\":6201242631},\"timestamp\":1636539275},{\"id\":\"618b9b865199f2191a0bf6ab\",\"user_id\":2114440,\"target\":1778268,\"type\":\"stats\",\"report\":{\"defense\":1556342777},\"timestamp\":1636539270},{\"id\":\"618ab0b24df233436f6a587a\",\"user_id\":2114440,\"target\":1403598,\"type\":\"stats\",\"report\":{\"speed\":200141978,\"dexterity\":178712624,\"defense\":221259119,\"total_battlestats\":750271353},\"timestamp\":1636479154},{\"id\":\"618aa3582d46f2478b61a1ae\",\"user_id\":2114440,\"target\":1190902,\"type\":\"stats\",\"report\":{\"strength\":169233272,\"dexterity\":200750044,\"total_battlestats\":410693637},\"timestamp\":1636475736},{\"id\":\"618aa354d8054b6fb6662656\",\"user_id\":2114440,\"target\":1190902,\"type\":\"stats\",\"report\":{\"speed\":39612016,\"dexterity\":200750044,\"defense\":1098305},\"timestamp\":1636475732},{\"id\":\"618aa3429f736a78f27a9280\",\"user_id\":2114440,\"target\":2154653,\"type\":\"stats\",\"report\":{\"strength\":14745,\"dexterity\":8801,\"defense\":34666,\"total_battlestats\":112989},\"timestamp\":1636475714},{\"id\":\"618aa33650bed1339846f8dd\",\"user_id\":2114440,\"target\":48429,\"type\":\"stats\",\"report\":{\"strength\":5497048,\"speed\":5495379,\"dexterity\":5495802,\"defense\":13391,\"total_battlestats\":16501620},\"timestamp\":1636475702},{\"id\":\"618aa323dd6412324a420dc5\",\"user_id\":2114440,\"target\":428220,\"type\":\"stats\",\"report\":{\"strength\":1062520207,\"dexterity\":2460056289,\"defense\":939839630,\"total_battlestats\":5538497499},\"timestamp\":1636475683},{\"id\":\"618aa31d0d5e20539b62a21d\",\"user_id\":2114440,\"target\":428220,\"type\":\"stats\",\"report\":{\"speed\":1076081373,\"defense\":939839630,\"total_battlestats\":5538497499},\"timestamp\":1636475677},{\"id\":\"6185af453ba6c64b1b587499\",\"user_id\":2114440,\"target\":2246920,\"type\":\"stats\",\"report\":{\"speed\":480769532,\"defense\":743031390,\"total_battlestats\":1817184680},\"timestamp\":1636151109},{\"id\":\"6185ade4eddb9b55727a7101\",\"user_id\":2114440,\"target\":2174905,\"type\":\"stats\",\"report\":{\"strength\":489539592,\"speed\":599119477,\"dexterity\":587167180,\"total_battlestats\":2157075465},\"timestamp\":1636150756},{\"id\":\"6185adbaa87a9111e357f4c6\",\"user_id\":2114440,\"target\":2235955,\"type\":\"stats\",\"report\":{\"strength\":363772339,\"speed\":227114026,\"dexterity\":1000276231,\"defense\":250065163},\"timestamp\":1636150714},{\"id\":\"6185adb54b21a84b5302948b\",\"user_id\":2114440,\"target\":2235955,\"type\":\"stats\",\"report\":{\"dexterity\":1000276231,\"defense\":250065163,\"total_battlestats\":1841227759},\"timestamp\":1636150709},{\"id\":\"617c132ea8c0d053534e9568\",\"user_id\":2114440,\"target\":1418240,\"type\":\"stats\",\"report\":{\"strength\":588887295,\"speed\":301166221,\"dexterity\":336971447,\"defense\":426374686,\"total_battlestats\":1653399649},\"timestamp\":1635521326},{\"id\":\"617c1311ca2a111ab77e45ac\",\"user_id\":2114440,\"target\":1618846,\"type\":\"stats\",\"report\":{\"strength\":1001774705,\"dexterity\":487189829,\"defense\":55201722},\"timestamp\":1635521297},{\"id\":\"617c130beb20084fe01d530b\",\"user_id\":2114440,\"target\":1618846,\"type\":\"stats\",\"report\":{\"strength\":1001774705,\"speed\":537920493,\"defense\":55201722},\"timestamp\":1635521291},{\"id\":\"6176f040517c11094940ee90\",\"user_id\":2114440,\"target\":2128726,\"type\":\"stats\",\"report\":{\"strength\":525042015,\"speed\":505975676,\"dexterity\":1001318260,\"defense\":715611341,\"total_battlestats\":2747947292},\"timestamp\":1635184704},{\"id\":\"6176f03bf151d548d2127b67\",\"user_id\":2114440,\"target\":2128726,\"type\":\"stats\",\"report\":{\"strength\":525042015,\"speed\":505975676,\"total_battlestats\":2747947292},\"timestamp\":1635184699},{\"id\":\"615741c1c8f7d02ebc300871\",\"user_id\":2114440,\"target\":1999292,\"type\":\"stats\",\"report\":{\"strength\":130163279,\"speed\":130312134,\"defense\":85696045,\"total_battlestats\":346275229},\"timestamp\":1633108417},{\"id\":\"61557954deac2824df179720\",\"user_id\":2114440,\"target\":2447134,\"type\":\"stats\",\"report\":{\"strength\":10125624,\"speed\":3789664,\"dexterity\":10118654,\"defense\":10147274,\"total_battlestats\":34181216},\"timestamp\":1632991572},{\"id\":\"61537e91fc19034dfe330e91\",\"user_id\":2114440,\"target\":1560293,\"type\":\"stats\",\"report\":{\"strength\":857511842,\"speed\":766936980,\"total_battlestats\":3124976087},\"timestamp\":1632861841},{\"id\":\"61446ec9e9c53c61e9534d08\",\"user_id\":2114440,\"target\":238789,\"type\":\"stats\",\"report\":{\"strength\":344097695,\"speed\":263833987,\"dexterity\":252604236,\"defense\":250517401},\"timestamp\":1631874761},{\"id\":\"6143919966df5d328e104743\",\"user_id\":2114440,\"target\":750076,\"type\":\"stats\",\"report\":{\"strength\":177741108,\"speed\":146442937,\"dexterity\":1675111,\"total_battlestats\":531729935},\"timestamp\":1631818137},{\"id\":\"61439186b2a6a647ba7cb608\",\"user_id\":2114440,\"target\":543469,\"type\":\"stats\",\"report\":{\"strength\":850961520,\"speed\":900331897,\"total_battlestats\":3421045770},\"timestamp\":1631818118},{\"id\":\"613dc54537865e4b094dc4ca\",\"user_id\":2114440,\"target\":1988849,\"type\":\"stats\",\"report\":{\"speed\":351693130,\"total_battlestats\":1142199238},\"timestamp\":1631438149},{\"id\":\"613dc54129957a4d023a3296\",\"user_id\":2114440,\"target\":1988849,\"type\":\"stats\",\"report\":{\"dexterity\":313185755,\"defense\":124105996},\"timestamp\":1631438145},{\"id\":\"613dc53d3889344f460b423b\",\"user_id\":2114440,\"target\":1988849,\"type\":\"stats\",\"report\":{\"dexterity\":313185755,\"defense\":124105996},\"timestamp\":1631438141},{\"id\":\"613c92ad5f31627aa16c29c3\",\"user_id\":2114440,\"target\":2187633,\"type\":\"stats\",\"report\":{\"speed\":135989572,\"dexterity\":1042119,\"defense\":200404972,\"total_battlestats\":537464072},\"timestamp\":1631359661},{\"id\":\"613c92a91a03432660538827\",\"user_id\":2114440,\"target\":2187633,\"type\":\"stats\",\"report\":{\"strength\":200027409,\"speed\":135989572,\"defense\":200404972},\"timestamp\":1631359657},{\"id\":\"613c91691ad4ec53061b5f54\",\"user_id\":2114440,\"target\":420502,\"type\":\"stats\",\"report\":{\"strength\":402467265,\"speed\":415602510,\"defense\":303449468,\"total_battlestats\":1535427679},\"timestamp\":1631359337},{\"id\":\"613c8abbd024b04d3a788b63\",\"user_id\":2114440,\"target\":1834771,\"type\":\"stats\",\"report\":{\"strength\":726527447,\"speed\":409230752,\"dexterity\":435362599,\"defense\":2634589654,\"total_battlestats\":4205710452},\"timestamp\":1631357627},{\"id\":\"613c8ab65bba3821db59c34e\",\"user_id\":2114440,\"target\":1834771,\"type\":\"stats\",\"report\":{\"defense\":2634589654,\"total_battlestats\":4205710452},\"timestamp\":1631357622},{\"id\":\"613c8aa96fc4b01f00515936\",\"user_id\":2114440,\"target\":1834771,\"type\":\"stats\",\"report\":{\"strength\":726527447,\"total_battlestats\":4205710452},\"timestamp\":1631357609},{\"id\":\"613b9e6cf1704965fc7a917f\",\"user_id\":2114440,\"target\":574315,\"type\":\"stats\",\"report\":{\"strength\":525811903,\"dexterity\":1978739426,\"defense\":389139778,\"total_battlestats\":3454458380},\"timestamp\":1631297132},{\"id\":\"613b9e680b99512be950e543\",\"user_id\":2114440,\"target\":574315,\"type\":\"stats\",\"report\":{\"speed\":560767273,\"defense\":389139778,\"total_battlestats\":3454458380},\"timestamp\":1631297128},{\"id\":\"6132169d9f42227879420e73\",\"user_id\":2114440,\"target\":2430598,\"type\":\"stats\",\"report\":{\"strength\":335972688,\"dexterity\":16501100,\"defense\":468808086,\"total_battlestats\":1146097520},\"timestamp\":1630672541},{\"id\":\"6132169449a9e45a2546bd14\",\"user_id\":2114440,\"target\":2430598,\"type\":\"stats\",\"report\":{\"strength\":335972688,\"dexterity\":16501100},\"timestamp\":1630672532},{\"id\":\"6132168d0c0231188e6b09ad\",\"user_id\":2114440,\"target\":2430598,\"type\":\"stats\",\"report\":{\"strength\":335972688,\"speed\":324815646,\"dexterity\":16501100},\"timestamp\":1630672525},{\"id\":\"6131faff8e74ae0c851fe080\",\"user_id\":2114440,\"target\":2158905,\"type\":\"stats\",\"report\":{\"strength\":4002964,\"total_battlestats\":9304644},\"timestamp\":1630665471},{\"id\":\"6131faf51acc5d234925827d\",\"user_id\":2114440,\"target\":2158905,\"type\":\"stats\",\"report\":{\"speed\":2284790,\"dexterity\":1000038},\"timestamp\":1630665461},{\"id\":\"6131f79b57c7007f4b63f156\",\"user_id\":2114440,\"target\":2158905,\"type\":\"stats\",\"report\":{\"strength\":4002964,\"speed\":2284790,\"dexterity\":1000038,\"total_battlestats\":9304644},\"timestamp\":1630664603},{\"id\":\"6131f701dc2b197d8615a5f7\",\"user_id\":2114440,\"target\":2158905,\"type\":\"stats\",\"report\":{\"strength\":4002964,\"speed\":2284790,\"dexterity\":1000038,\"defense\":2016852},\"timestamp\":1630664449},{\"id\":\"60f55717bf1f81412624076e\",\"user_id\":2114440,\"target\":1936821,\"type\":\"stats\",\"report\":{\"total_battlestats\":22290},\"timestamp\":1626691351},{\"id\":\"60f55717bf1f81412624076f\",\"user_id\":2114440,\"target\":1936821,\"type\":\"money\",\"report\":{\"money\":532039},\"timestamp\":1626691351},{\"id\":\"60f5571020a8ff118f34010b\",\"user_id\":2114440,\"target\":1936821,\"type\":\"stats\",\"report\":{\"strength\":18277,\"total_battlestats\":22290},\"timestamp\":1626691344},{\"id\":\"60f5571020a8ff118f34010c\",\"user_id\":2114440,\"target\":1936821,\"type\":\"money\",\"report\":{\"money\":532039},\"timestamp\":1626691344},{\"id\":\"60f556fcebfc28242a48784e\",\"user_id\":2114440,\"target\":2087524,\"type\":\"stats\",\"report\":{\"speed\":58658184,\"dexterity\":73221660,\"defense\":69716152,\"total_battlestats\":330468583},\"timestamp\":1626691324},{\"id\":\"60f556fcebfc28242a48784f\",\"user_id\":2114440,\"target\":2087524,\"type\":\"money\",\"report\":{\"money\":125259},\"timestamp\":1626691324},{\"id\":\"60ed79b6d910af3c800872e8\",\"user_id\":2114440,\"target\":1373890,\"type\":\"stats\",\"report\":{\"speed\":716578726,\"defense\":1307424609},\"timestamp\":1626175926},{\"id\":\"60ed79b6d910af3c800872e9\",\"user_id\":2114440,\"target\":1373890,\"type\":\"money\",\"report\":{\"money\":1242674},\"timestamp\":1626175926},{\"id\":\"60ed79a7f3865e2d8205a60d\",\"user_id\":2114440,\"target\":1373890,\"type\":\"stats\",\"report\":{\"speed\":716578726,\"dexterity\":639782125},\"timestamp\":1626175911},{\"id\":\"60ed79a7f3865e2d8205a60e\",\"user_id\":2114440,\"target\":1373890,\"type\":\"money\",\"report\":{\"money\":1242674},\"timestamp\":1626175911},{\"id\":\"60ed7883922b813cbc2306d9\",\"user_id\":2114440,\"target\":1373890,\"type\":\"stats\",\"report\":{\"speed\":716578726,\"dexterity\":639782125,\"defense\":1307424609,\"total_battlestats\":3736273885},\"timestamp\":1626175619},{\"id\":\"60ed7883922b813cbc2306da\",\"user_id\":2114440,\"target\":1373890,\"type\":\"money\",\"report\":{\"money\":1231862},\"timestamp\":1626175619},{\"id\":\"60de182de09e936c1d139db8\",\"user_id\":2114440,\"target\":2087524,\"type\":\"money\",\"report\":{\"money\":31841},\"timestamp\":1625167917},{\"id\":\"60de182434115411f85d2fc1\",\"user_id\":2114440,\"target\":2158905,\"type\":\"money\",\"report\":{\"money\":883481},\"timestamp\":1625167908},{\"id\":\"60d712624a53ea66fd6fc0f4\",\"user_id\":2114440,\"target\":1878147,\"type\":\"stats\",\"report\":{\"strength\":697046166,\"speed\":601122052,\"defense\":1010336018,\"total_battlestats\":2926812332},\"timestamp\":1624707682},{\"id\":\"60d7125c4166e11a0c1d0764\",\"user_id\":2114440,\"target\":1878147,\"type\":\"stats\",\"report\":{\"strength\":697046166,\"speed\":601122052},\"timestamp\":1624707676},{\"id\":\"60d4ee7e9a4ab14d703696e6\",\"user_id\":2114440,\"target\":1932248,\"type\":\"stats\",\"report\":{\"strength\":16950839,\"speed\":1466995,\"dexterity\":6038873,\"total_battlestats\":48673113},\"timestamp\":1624567422},{\"id\":\"60bcb6b3e2d4787ef73260ab\",\"user_id\":2114440,\"target\":353853,\"type\":\"stats\",\"report\":{\"strength\":1502607043,\"dexterity\":782776870,\"defense\":204837677,\"total_battlestats\":3630557789},\"timestamp\":1622980275},{\"id\":\"60bca23961059d5fd50f2511\",\"user_id\":2114440,\"target\":1300884,\"type\":\"stats\",\"report\":{\"strength\":501819539,\"dexterity\":304719092,\"defense\":1373404917,\"total_battlestats\":2546108709},\"timestamp\":1622975033},{\"id\":\"60b430ff7e222844eb39a5ee\",\"user_id\":2114440,\"target\":2249291,\"type\":\"stats\",\"report\":{\"strength\":534775914,\"dexterity\":104997695,\"defense\":614558706,\"total_battlestats\":2264522778},\"timestamp\":1622421759},{\"id\":\"60b430a69cc1ae65325465ba\",\"user_id\":2114440,\"target\":2210034,\"type\":\"stats\",\"report\":{\"strength\":501248455,\"dexterity\":6919,\"defense\":655235384,\"total_battlestats\":1656957446},\"timestamp\":1622421670},{\"id\":\"60b42e572f5cab6075337a29\",\"user_id\":2114440,\"target\":9972,\"type\":\"stats\",\"report\":{\"speed\":312000015,\"dexterity\":382760722,\"total_battlestats\":1368647466},\"timestamp\":1622421079},{\"id\":\"60b42e2dcb119f5c197c98d8\",\"user_id\":2114440,\"target\":9972,\"type\":\"stats\",\"report\":{\"dexterity\":382760722,\"defense\":376856399},\"timestamp\":1622421037},{\"id\":\"60b42d807184286f565e7500\",\"user_id\":2114440,\"target\":133606,\"type\":\"stats\",\"report\":{\"strength\":500657770,\"speed\":372778592,\"dexterity\":1000032237,\"defense\":168670,\"total_battlestats\":1873637269},\"timestamp\":1622420864},{\"id\":\"60b42b697018a925c340cf57\",\"user_id\":2114440,\"target\":133606,\"type\":\"stats\",\"report\":{\"strength\":500657770,\"speed\":372778592,\"dexterity\":1000032237,\"defense\":168670},\"timestamp\":1622420329},{\"id\":\"60b42a02aa6f601c6f6da097\",\"user_id\":2114440,\"target\":133606,\"type\":\"stats\",\"report\":{\"strength\":500657770,\"speed\":372778592,\"dexterity\":1000032237,\"defense\":168670},\"timestamp\":1622419970},{\"id\":\"60b429506bd06a142b58fe8f\",\"user_id\":2114440,\"target\":1672553,\"type\":\"stats\",\"report\":{\"strength\":231646770,\"dexterity\":145210618,\"defense\":230067849,\"total_battlestats\":767643872},\"timestamp\":1622419792},{\"id\":\"60b42915f1d39e2d832b325c\",\"user_id\":2114440,\"target\":1672553,\"type\":\"stats\",\"report\":{\"strength\":231646770,\"speed\":160718635,\"dexterity\":145210618,\"defense\":230067849,\"total_battlestats\":767643872},\"timestamp\":1622419733},{\"id\":\"60b42899f6c73e151c311fc4\",\"user_id\":2114440,\"target\":1672553,\"type\":\"stats\",\"report\":{\"strength\":231646770,\"dexterity\":145210618,\"total_battlestats\":767643872},\"timestamp\":1622419609},{\"id\":\"60b4284799ced11e7c22f8d0\",\"user_id\":2114440,\"target\":1672553,\"type\":\"stats\",\"report\":{\"strength\":231646770,\"speed\":160718635,\"defense\":230067849},\"timestamp\":1622419527},{\"id\":\"60b42802378ccc07c35d0347\",\"user_id\":2114440,\"target\":239697,\"type\":\"stats\",\"report\":{\"speed\":597759290,\"dexterity\":605207430,\"defense\":4750,\"total_battlestats\":2011385559},\"timestamp\":1622419458},{\"id\":\"60b422a65066c3149a5f4e75\",\"user_id\":2114440,\"target\":1132931,\"type\":\"stats\",\"report\":{\"strength\":32352376,\"speed\":33331598,\"dexterity\":31712079,\"defense\":855199203,\"total_battlestats\":952595256},\"timestamp\":1622418086},{\"id\":\"60ab77c2095bbe17df0857c9\",\"user_id\":2114440,\"target\":1946760,\"type\":\"stats\",\"report\":{\"strength\":14138237,\"speed\":10228103,\"defense\":43729234},\"timestamp\":1621850050},{\"id\":\"60ab77bd5ae1df2d31606e88\",\"user_id\":2114440,\"target\":1946760,\"type\":\"stats\",\"report\":{\"strength\":14138237,\"speed\":10228103,\"total_battlestats\":70131108},\"timestamp\":1621850045},{\"id\":\"60993f35ef9bfb2876450bf1\",\"user_id\":2114440,\"target\":1890780,\"type\":\"stats\",\"report\":{\"strength\":4525801,\"speed\":4710186,\"dexterity\":162276,\"defense\":1147560937,\"total_battlestats\":1156959200},\"timestamp\":1620655925},{\"id\":\"60993f2f3b04f50bc94a2eaa\",\"user_id\":2114440,\"target\":1890780,\"type\":\"stats\",\"report\":{\"dexterity\":162276,\"defense\":1147560937,\"total_battlestats\":1156959200},\"timestamp\":1620655919},{\"id\":\"609314bfade7131b6209d54c\",\"user_id\":2114440,\"target\":2154572,\"type\":\"stats\",\"report\":{\"strength\":41230},\"timestamp\":1620251839},{\"id\":\"609314ba881d0d7e415fac28\",\"user_id\":2114440,\"target\":2154572,\"type\":\"stats\",\"report\":{\"dexterity\":498308,\"defense\":180209,\"total_battlestats\":760941},\"timestamp\":1620251834},{\"id\":\"609314ae13239f4f605403aa\",\"user_id\":2114440,\"target\":2078598,\"type\":\"stats\",\"report\":{\"speed\":1041265,\"dexterity\":1085041,\"defense\":3581724,\"total_battlestats\":7645723},\"timestamp\":1620251822},{\"id\":\"6093149fecddd327f30945e0\",\"user_id\":2114440,\"target\":1694376,\"type\":\"stats\",\"report\":{\"strength\":13503706,\"speed\":13441064,\"defense\":15352542,\"total_battlestats\":65411155},\"timestamp\":1620251807},{\"id\":\"6093149a35eaea27f966b5ec\",\"user_id\":2114440,\"target\":1694376,\"type\":\"stats\",\"report\":{\"speed\":13441064,\"defense\":15352542,\"total_battlestats\":65411155},\"timestamp\":1620251802},{\"id\":\"60931491df14a524f0489612\",\"user_id\":2114440,\"target\":2149263,\"type\":\"stats\",\"report\":{\"strength\":10097194,\"speed\":10085452,\"dexterity\":2033249,\"defense\":1949093,\"total_battlestats\":24164988},\"timestamp\":1620251793},{\"id\":\"6093148d4f8ece7c5c676311\",\"user_id\":2114440,\"target\":2149263,\"type\":\"stats\",\"report\":{\"strength\":10097194,\"defense\":1949093,\"total_battlestats\":24164988},\"timestamp\":1620251789},{\"id\":\"60931466db68603ed07d45da\",\"user_id\":2114440,\"target\":2352055,\"type\":\"stats\",\"report\":{\"speed\":100083442},\"timestamp\":1620251750}]}");

        // Act
        List<Reports> result = UserMapper.ofReports(json);

        // Assert
        Reports.Report reportDetails1 = new Reports.Report();
        reportDetails1.setStrength(10095484);
        reportDetails1.setSpeed(10022851);
        reportDetails1.setDexterity(4828144);
        reportDetails1.setDefense(13230202);
        reportDetails1.setTotalBattleStats(38176681);

        Reports report1 = new Reports();
        report1.setId("619bc6a27e9adc6ad73b6bc1");
        report1.setUserId(2114440);
        report1.setTarget(2616736);
        report1.setType(Reports.ReportType.STATS);
        report1.setReport(reportDetails1);
        report1.setTimestamp(LocalDateTime.of(2021, 11, 22, 16, 34, 42));

        Reports.Report reportDetails2 = new Reports.Report();
        reportDetails2.setStrength(537214725);
        reportDetails2.setSpeed(343973904);
        reportDetails2.setDexterity(810461471);
        reportDetails2.setTotalBattleStats(2047708930);

        Reports report2 = new Reports();
        report2.setId("618d074e144bea637f73f46c");
        report2.setUserId(2114440);
        report2.setTarget(443688);
        report2.setType(Reports.ReportType.STATS);
        report2.setReport(reportDetails2);
        report2.setTimestamp(LocalDateTime.of(2021, 11, 11, 12, 6, 38));

        assertThat(result)
                .hasSize(100)
                .contains(report1, report2);
    }

    @Test
    void ofRevivesFull() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"revives\":{\"4521338\":{\"timestamp\":1636160789,\"result\":\"success\",\"chance\":100,\"reviver_id\":2235232,\"reviver_faction\":17133,\"target_id\":2114440,\"target_faction\":33007,\"target_hospital_reason\":\"Hospitalized by <a href = \\\"http://www.torn.com/profiles.php?XID=734492\\\">BabyLuST</a>\",\"target_early_discharge\":0,\"target_last_action\":{\"status\":\"Online\",\"timestamp\":1636160783}},\"4524700\":{\"timestamp\":1636193289,\"result\":\"success\",\"chance\":96.69,\"reviver_id\":2543367,\"reviver_faction\":21028,\"target_id\":2114440,\"target_faction\":33007,\"target_hospital_reason\":\"Overdosed on Xanax\",\"target_early_discharge\":0,\"target_last_action\":{\"status\":\"Online\",\"timestamp\":1636193244}},\"4648307\":{\"timestamp\":1637536174,\"result\":\"success\",\"chance\":96.11,\"reviver_id\":363281,\"reviver_faction\":17133,\"target_id\":2114440,\"target_faction\":33007,\"target_hospital_reason\":\"Hospitalized by <a href = \\\"http://www.torn.com/profiles.php?XID=1456295\\\">Nepy</a>\",\"target_early_discharge\":0,\"target_last_action\":{\"status\":\"Offline\",\"timestamp\":1637533844}}}}\n");

        // Act
        Map<Long, Revives> result = UserMapper.ofRevives(json);

        // Assert
        Revives.LastAction reviveLastAction1 = new Revives.LastAction();
        reviveLastAction1.setStatus(LastActionStatus.ONLINE);
        reviveLastAction1.setTimestamp(LocalDateTime.of(2021, 11, 6, 1, 6, 23));


        Revives revive1 = new Revives();
        revive1.setTimestamp(LocalDateTime.of(2021, 11, 6, 1, 6, 29));
        revive1.setResult(Revives.Result.SUCCESS);
        revive1.setChance(100);
        revive1.setReviverId(2235232);
        revive1.setReviverFaction(17133);
        revive1.setTargetId(2114440);
        revive1.setTargetFaction(33007);
        revive1.setTargetHospitalReason("Hospitalized by <a href = \"http://www.torn.com/profiles.php?XID=734492\">BabyLuST</a>");
        revive1.setTargetEarlyDischarge(false);
        revive1.setTargetLastAction(reviveLastAction1);

        Revives.LastAction reviveLastAction2 = new Revives.LastAction();
        reviveLastAction2.setStatus(LastActionStatus.OFFLINE);
        reviveLastAction2.setTimestamp(LocalDateTime.of(2021, 11, 21, 22, 30, 44));

        Revives revive2 = new Revives();
        revive2.setTimestamp(LocalDateTime.of(2021, 11, 21, 23, 9, 34));
        revive2.setResult(Revives.Result.SUCCESS);
        revive2.setChance(96.11);
        revive2.setReviverId(363281);
        revive2.setReviverFaction(17133);
        revive2.setTargetId(2114440);
        revive2.setTargetFaction(33007);
        revive2.setTargetHospitalReason("Hospitalized by <a href = \"http://www.torn.com/profiles.php?XID=1456295\">Nepy</a>");
        revive2.setTargetEarlyDischarge(false);
        revive2.setTargetLastAction(reviveLastAction2);

        assertThat(result)
                .hasSize(3)
                .containsEntry(4521338L, revive1)
                .containsEntry(4648307L, revive2);
    }

    @Test
    void ofRevives() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"revives\":{\"4521338\":{\"timestamp\":1636160789,\"result\":\"success\",\"chance\":100,\"reviver_id\":2235232,\"reviver_name\":\"ScarletStorm\",\"reviver_faction\":17133,\"reviver_factionname\":\"Torn Medical\",\"target_id\":2114440,\"target_name\":\"DeKleineKobini\",\"target_faction\":33007,\"target_factionname\":\"HAKA\",\"target_hospital_reason\":\"Hospitalized by <a href = \\\"http://www.torn.com/profiles.php?XID=734492\\\">BabyLuST</a>\",\"target_early_discharge\":0,\"target_last_action\":{\"status\":\"Online\",\"timestamp\":1636160783}},\"4524700\":{\"timestamp\":1636193289,\"result\":\"success\",\"chance\":96.69,\"reviver_id\":2543367,\"reviver_name\":\"Afatha\",\"reviver_faction\":21028,\"reviver_factionname\":\"Nuclear Clinic\",\"target_id\":2114440,\"target_name\":\"DeKleineKobini\",\"target_faction\":33007,\"target_factionname\":\"HAKA\",\"target_hospital_reason\":\"Overdosed on Xanax\",\"target_early_discharge\":0,\"target_last_action\":{\"status\":\"Online\",\"timestamp\":1636193244}},\"4648307\":{\"timestamp\":1637536174,\"result\":\"success\",\"chance\":96.11,\"reviver_id\":363281,\"reviver_name\":\"goobs\",\"reviver_faction\":17133,\"reviver_factionname\":\"Torn Medical\",\"target_id\":2114440,\"target_name\":\"DeKleineKobini\",\"target_faction\":33007,\"target_factionname\":\"HAKA\",\"target_hospital_reason\":\"Hospitalized by <a href = \\\"http://www.torn.com/profiles.php?XID=1456295\\\">Nepy</a>\",\"target_early_discharge\":0,\"target_last_action\":{\"status\":\"Offline\",\"timestamp\":1637533844}}}}");

        // Act
        Map<Long, Revives> result = UserMapper.ofRevives(json);

        // Assert
        Revives.LastAction reviveLastAction1 = new Revives.LastAction();
        reviveLastAction1.setStatus(LastActionStatus.ONLINE);
        reviveLastAction1.setTimestamp(LocalDateTime.of(2021, 11, 6, 1, 6, 23));

        Revives revive1 = new Revives();
        revive1.setTimestamp(LocalDateTime.of(2021, 11, 6, 1, 6, 29));
        revive1.setResult(Revives.Result.SUCCESS);
        revive1.setChance(100);
        revive1.setReviverId(2235232);
        revive1.setReviverName("ScarletStorm");
        revive1.setReviverFaction(17133);
        revive1.setReviverFactionName("Torn Medical");
        revive1.setTargetId(2114440);
        revive1.setTargetName("DeKleineKobini");
        revive1.setTargetFaction(33007);
        revive1.setTargetFactionName("HAKA");
        revive1.setTargetHospitalReason("Hospitalized by <a href = \"http://www.torn.com/profiles.php?XID=734492\">BabyLuST</a>");
        revive1.setTargetEarlyDischarge(false);
        revive1.setTargetLastAction(reviveLastAction1);

        Revives.LastAction reviveLastAction2 = new Revives.LastAction();
        reviveLastAction2.setStatus(LastActionStatus.OFFLINE);
        reviveLastAction2.setTimestamp(LocalDateTime.of(2021, 11, 21, 22, 30, 44));

        Revives revive2 = new Revives();
        revive2.setTimestamp(LocalDateTime.of(2021, 11, 21, 23, 9, 34));
        revive2.setResult(Revives.Result.SUCCESS);
        revive2.setChance(96.11);
        revive2.setReviverId(363281);
        revive2.setReviverName("goobs");
        revive2.setReviverFaction(17133);
        revive2.setReviverFactionName("Torn Medical");
        revive2.setTargetId(2114440);
        revive2.setTargetName("DeKleineKobini");
        revive2.setTargetFaction(33007);
        revive2.setTargetFactionName("HAKA");
        revive2.setTargetHospitalReason("Hospitalized by <a href = \"http://www.torn.com/profiles.php?XID=1456295\">Nepy</a>");
        revive2.setTargetEarlyDischarge(false);
        revive2.setTargetLastAction(reviveLastAction2);

        assertThat(result)
                .hasSize(3)
                .containsEntry(4521338L, revive1)
                .containsEntry(4648307L, revive2);
    }

    @Test
    void ofSkills() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"reviving\":\"100.00\",\"hunting\":\"62.74\",\"racing\":\"18.73\",\"player_id\":2114440}");

        // Act
        Skills result = UserMapper.ofSkills(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(result.getReviving()).isEqualTo(100);
        softly.assertThat(result.getHunting()).isEqualTo(62.74);
        softly.assertThat(result.getRacing()).isEqualTo(18.73);
        softly.assertThat(result.getPlayerId()).isEqualTo(2114440);

        softly.assertAll();
    }

    @Test
    void ofStocks() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"stocks\":{\"1\":{\"stock_id\":1,\"total_shares\":3000000,\"dividend\":{\"ready\":0,\"increment\":1,\"progress\":25,\"frequency\":31},\"transactions\":{\"1680534\":{\"shares\":3000000,\"bought_price\":885.61,\"time_bought\":1633181321}}},\"2\":{\"stock_id\":2,\"total_shares\":1500000,\"benefit\":{\"ready\":1,\"increment\":1,\"progress\":7,\"frequency\":7},\"transactions\":{\"1910085\":{\"shares\":1500000,\"bought_price\":985.95,\"time_bought\":1636324898}}},\"5\":{\"stock_id\":5,\"total_shares\":3000000,\"dividend\":{\"ready\":0,\"increment\":1,\"progress\":18,\"frequency\":31},\"transactions\":{\"223959\":{\"shares\":3000000,\"bought_price\":147.88,\"time_bought\":1617714000}}},\"6\":{\"stock_id\":6,\"total_shares\":1500000,\"dividend\":{\"ready\":0,\"increment\":2,\"progress\":18,\"frequency\":31},\"transactions\":{\"338059\":{\"shares\":1500000,\"bought_price\":213.35,\"time_bought\":1617737352}}},\"7\":{\"stock_id\":7,\"total_shares\":150000,\"dividend\":{\"ready\":0,\"increment\":1,\"progress\":4,\"frequency\":7},\"transactions\":{\"223951\":{\"shares\":150000,\"bought_price\":327.5,\"time_bought\":1617714000}}},\"9\":{\"stock_id\":9,\"total_shares\":300000,\"dividend\":{\"ready\":0,\"increment\":2,\"progress\":18,\"frequency\":31},\"transactions\":{\"338151\":{\"shares\":300000,\"bought_price\":209.64,\"time_bought\":1617737467}}},\"12\":{\"stock_id\":12,\"total_shares\":6000000,\"dividend\":{\"ready\":0,\"increment\":1,\"progress\":18,\"frequency\":31},\"transactions\":{\"682860\":{\"shares\":6000000,\"bought_price\":150.36,\"time_bought\":1620390529}}},\"15\":{\"stock_id\":15,\"total_shares\":6000000,\"dividend\":{\"ready\":0,\"increment\":2,\"progress\":4,\"frequency\":7},\"transactions\":{\"1114160\":{\"shares\":6000000,\"bought_price\":662.33,\"time_bought\":1625576887}}},\"16\":{\"stock_id\":16,\"total_shares\":1500000,\"dividend\":{\"ready\":0,\"increment\":2,\"progress\":4,\"frequency\":7},\"transactions\":{\"335645\":{\"shares\":1500000,\"bought_price\":732.01,\"time_bought\":1617734396}}},\"17\":{\"stock_id\":17,\"total_shares\":500000,\"dividend\":{\"ready\":0,\"increment\":1,\"progress\":4,\"frequency\":7},\"transactions\":{\"315588\":{\"shares\":500000,\"bought_price\":340.61,\"time_bought\":1617716605}}},\"18\":{\"stock_id\":18,\"total_shares\":1000000,\"dividend\":{\"ready\":0,\"increment\":1,\"progress\":4,\"frequency\":7},\"transactions\":{\"338994\":{\"shares\":1000000,\"bought_price\":479.17,\"time_bought\":1617738452}}},\"19\":{\"stock_id\":19,\"total_shares\":1000000,\"dividend\":{\"ready\":0,\"increment\":1,\"progress\":4,\"frequency\":7},\"transactions\":{\"1680536\":{\"shares\":1000000,\"bought_price\":235.63,\"time_bought\":1633181389}}},\"24\":{\"stock_id\":24,\"total_shares\":5000000,\"dividend\":{\"ready\":0,\"increment\":1,\"progress\":4,\"frequency\":7},\"transactions\":{\"1117412\":{\"shares\":5000000,\"bought_price\":421.43,\"time_bought\":1625604531}}},\"29\":{\"stock_id\":29,\"total_shares\":2450000,\"dividend\":{\"ready\":0,\"increment\":3,\"progress\":4,\"frequency\":7},\"transactions\":{\"489139\":{\"shares\":2450000,\"bought_price\":568.48,\"time_bought\":1618315615}}},\"32\":{\"stock_id\":32,\"total_shares\":1000000,\"dividend\":{\"ready\":0,\"increment\":1,\"progress\":4,\"frequency\":7},\"transactions\":{\"1117624\":{\"shares\":1000000,\"bought_price\":261.17,\"time_bought\":1625606287}}}}}");

        // Act
        Map<Long, Stocks> result = UserMapper.ofStocks(json);

        // Assert
        Stocks.Dividend tsbDividend = new Stocks.Dividend();
        tsbDividend.setReady(false);
        tsbDividend.setIncrement(1);
        tsbDividend.setProgress(25);
        tsbDividend.setFrequency(31);

        Stocks.Transaction tsbTransaction = new Stocks.Transaction();
        tsbTransaction.setShares(3000000);
        tsbTransaction.setBoughtPrice(885.61f);
        tsbTransaction.setTimeBought(LocalDateTime.of(2021, 10, 2, 13, 28, 41));

        Stocks tsb = new Stocks();
        tsb.setStockId(1);
        tsb.setTotalShares(3000000);
        tsb.setDividend(tsbDividend);
        tsb.setTransactions(new HashMap<>() {{
            put(1680534L, tsbTransaction);
        }});

        Stocks.Dividend tmiDividend = new Stocks.Dividend();
        tmiDividend.setReady(false);
        tmiDividend.setIncrement(1);
        tmiDividend.setProgress(18);
        tmiDividend.setFrequency(31);

        Stocks.Transaction tmiTransaction = new Stocks.Transaction();
        tmiTransaction.setShares(6000000);
        tmiTransaction.setBoughtPrice(150.36f);
        tmiTransaction.setTimeBought(LocalDateTime.of(2021, 5, 7, 12, 28, 49));

        Stocks tmi = new Stocks();
        tmi.setStockId(12);
        tmi.setTotalShares(6000000);
        tmi.setDividend(tmiDividend);
        tmi.setTransactions(new HashMap<>() {{
            put(682860L, tmiTransaction);
        }});

        assertThat(result)
                .hasSize(15)
                .containsEntry(1L, tsb)
                .containsEntry(12L, tmi);
    }

    @Test
    void ofTravel() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"travel\":{\"destination\":\"Switzerland\",\"method\":\"Airstrip\",\"timestamp\":1637448692,\"departed\":1637441492,\"time_left\":7190}}");

        // Act
        Travel travel = UserMapper.ofTravel(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(travel.getDestination()).isEqualTo("Switzerland");
        softly.assertThat(travel.getMethod()).isEqualTo(Travel.TravelMethod.AIRSTRIP);
        softly.assertThat(travel.getTimestamp()).isEqualTo(LocalDateTime.of(2021, 11, 20, 22, 51, 32));
        softly.assertThat(travel.getDeparted()).isEqualTo(LocalDateTime.of(2021, 11, 20, 20, 51, 32));
        softly.assertThat(travel.getTimeLeft()).hasMinutes(119);

        softly.assertAll();
    }

    @Test
    void ofWeaponExp() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"weaponexp\":[{\"itemID\":175,\"name\":\"Taser\",\"exp\":100},{\"itemID\":8,\"name\":\"Axe\",\"exp\":100},{\"itemID\":10,\"name\":\"Chainsaw\",\"exp\":100},{\"itemID\":237,\"name\":\"Kodachi\",\"exp\":100},{\"itemID\":255,\"name\":\"Flamethrower\",\"exp\":100},{\"itemID\":395,\"name\":\"Metal Nunchakus\",\"exp\":100},{\"itemID\":614,\"name\":\"Diamond Bladed Knife\",\"exp\":100},{\"itemID\":399,\"name\":\"ArmaLite M-15A4\",\"exp\":100},{\"itemID\":147,\"name\":\"Rusty Sword\",\"exp\":100},{\"itemID\":26,\"name\":\"AK-47\",\"exp\":67},{\"itemID\":233,\"name\":\"BT MP9\",\"exp\":43},{\"itemID\":805,\"name\":\"Duke's Hammer\",\"exp\":42},{\"itemID\":792,\"name\":\"Penelope\",\"exp\":39},{\"itemID\":63,\"name\":\"Minigun\",\"exp\":38},{\"itemID\":440,\"name\":\"Pillow\",\"exp\":30},{\"itemID\":616,\"name\":\"Trout\",\"exp\":26},{\"itemID\":223,\"name\":\"Jackhammer\",\"exp\":25},{\"itemID\":21,\"name\":\"Dual 92G Berettas\",\"exp\":24},{\"itemID\":394,\"name\":\"Brick\",\"exp\":17},{\"itemID\":1,\"name\":\"Hammer\",\"exp\":13},{\"itemID\":219,\"name\":\"Enfield SA-80\",\"exp\":13},{\"itemID\":2,\"name\":\"Baseball Bat\",\"exp\":7},{\"itemID\":220,\"name\":\"Grenade\",\"exp\":5},{\"itemID\":22,\"name\":\"Sawed-Off Shotgun\",\"exp\":4},{\"itemID\":24,\"name\":\"MP5 Navy\",\"exp\":3},{\"itemID\":29,\"name\":\"M16 A2 Rifle\",\"exp\":3},{\"itemID\":483,\"name\":\"MP5k\",\"exp\":3},{\"itemID\":27,\"name\":\"M4A1 Colt Carbine\",\"exp\":2},{\"itemID\":232,\"name\":\"SIG 550\",\"exp\":2},{\"itemID\":241,\"name\":\"Bushmaster Carbon 15\",\"exp\":2},{\"itemID\":6,\"name\":\"Kitchen Knife\",\"exp\":2},{\"itemID\":248,\"name\":\"Qsz-92\",\"exp\":1},{\"itemID\":18,\"name\":\"Fiveseven\",\"exp\":1},{\"itemID\":487,\"name\":\"Thompson\",\"exp\":1},{\"itemID\":402,\"name\":\"Ice Pick\",\"exp\":1},{\"itemID\":484,\"name\":\"AK74U\",\"exp\":1},{\"itemID\":490,\"name\":\"Blunderbuss\",\"exp\":1},{\"itemID\":249,\"name\":\"SKS Carbine\",\"exp\":1}]}");

        // Act
        List<WeaponExp> result = UserMapper.ofWeaponExp(json);

        // Assert
        WeaponExp taser = new WeaponExp();
        taser.setItemId(175);
        taser.setName("Taser");
        taser.setExp((byte) 100);

        WeaponExp ak47 = new WeaponExp();
        ak47.setItemId(26);
        ak47.setName("AK-47");
        ak47.setExp((byte) 67);

        WeaponExp sksCarbine = new WeaponExp();
        sksCarbine.setItemId(249);
        sksCarbine.setName("SKS Carbine");
        sksCarbine.setExp((byte) 1);

        assertThat(result)
                .hasSize(38)
                .contains(taser, ak47, sksCarbine);
    }

    @Test
    void ofWorkStats() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree("{\"manual_labor\":18325,\"intelligence\":70312,\"endurance\":120589}");

        // Act
        WorkStats stats = UserMapper.ofWorkStats(json);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(stats.getManualLabor()).isEqualTo(18325);
        softly.assertThat(stats.getIntelligence()).isEqualTo(70312);
        softly.assertThat(stats.getEndurance()).isEqualTo(120589);

        softly.assertAll();
    }

}
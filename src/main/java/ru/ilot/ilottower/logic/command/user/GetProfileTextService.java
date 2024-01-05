package ru.ilot.ilottower.logic.command.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.ilot.ilottower.model.entities.Backpack;
import ru.ilot.ilottower.model.entities.geo.Location;
import ru.ilot.ilottower.model.entities.user.Player;
import ru.ilot.ilottower.model.entities.user.StatsPlayer;
import ru.ilot.ilottower.model.enums.PlayerGender;
import ru.ilot.ilottower.model.repository.user.PlayerRepository;
import ru.ilot.ilottower.telegram.keyboard.GetReplyKeyboardByState;
import ru.ilot.ilottower.telegram.response.Response;
import ru.ilot.ilottower.telegram.response.StringResponse;
import ru.ilot.ilottower.telegram.response.StringWithKeyboardResponse;

import java.util.Optional;

import static java.lang.StringTemplate.STR;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetProfileTextService {

    private final PlayerRepository playerRepository;

    private final GetReplyKeyboardByState getReplyKeyboardByState;

    private static final String CALL_SITE = "GetProfile";

    @Transactional
    public Response<String> getProfile(Long userId) {

        try {
            String resultText = "";
            var sb = new StringBuilder();
            Optional<Player> optionalPlayer = playerRepository.findById(userId);

            if (optionalPlayer.isEmpty()) {
                return new StringResponse("А Вы, собстно, кто?");
            } else {
                Player player = optionalPlayer.get();
                //db.Entry(playerProfile).Collection(p = > p.QuestState);

                StatsPlayer playerStats = player.getStats();
                //PkState.ChangePkStateLow(playerProfile);

//                var wo = new WalletOperation(playerProfile, db);
//                var balance = wo.GetBalance();
//                var balanceGolden = wo.GetBalance(2);

                Backpack backpack = player.getBackpack();
                Location location = player.getLocation();

                String gender = "";
                if (player.getGender().equals(PlayerGender.MALE)) {
                    gender = "👨‍🦱";
                } else {
                    gender = "👩";
                }
                //string locationType = LocationNaming(location.LocationType);
                String locationType = location.getLocationType().name();
                sb.append(STR."\{ gender } Игрок: \{ player.getUsername() }\n");

//                var nextLevelExpir = db.LevelExperiences.FirstOrDefault(e = > e.Level == playerProfile.Level + 1);
//                var nextLvlExpir = "...";
//                if (nextLevelExpir != null) {
//                    nextLvlExpir = nextLevelExpir.Experience.ToString();
//                }

                //sb.append(STR."🎖 \{player.getLevel()} 💫 {playerProfile.ExpCurrent}/{nextLvlExpir}");
                sb.append(STR."🎖 \{player.getLevel()} 💫 \{player.getExpCurrent()}\n");


                if (playerStats != null) {
                    //sb.append(STR."\{EmojiGenerator.GetPkStatus(playerProfile)} Здоровье: \{playerStats.getHpCurrent()}/\{playerStats.getHpTotal()}\n");
                    sb.append(STR."Здоровье: \{playerStats.getHpCurrent()}/\{playerStats.getHpTotal()}\n");
                    sb.append(STR."🗡 Атака: \{(playerStats.getAttackTotal())} 🛡 Защита: \{playerStats.getDefenceTotal()}\n");

                    sb.append("⚔️Боевая связка: /moves\n");
                }
                sb.append(STR."🗺 Локация:  \{location.getLevelId()} (\{location.getLocationX()}:\{location.getLocationY()}) - \{locationType}");

                //sb.append(STR."👝Кошелек: 💰 \{balance} ⚜️ \{balanceGolden}");
                //sb.append(STR."🎒Рюкзак \{WeightCalculator.CountWeightNow(playerProfile)}/\{backpack.getMaxCount()} /inv");
                sb.append(STR."🎒Рюкзак \{backpack.getMaxCount()} /inv\n");
                sb.append(STR."🧩Квесты: /quests\n");

                resultText = sb.toString();
                ReplyKeyboard replyKeyboard = getReplyKeyboardByState.getKeyboard(player);

                return new StringWithKeyboardResponse(resultText, replyKeyboard);
            }

        } catch (Exception ex) {
            log.error("Error in {}, called by {}", CALL_SITE, userId, ex);
            return new StringResponse("Произошла ошибка!");
        }
    }
}

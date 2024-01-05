package ru.ilot.ilottower.telegram.keyboard;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Service
public class WalkingReplyKeyboardGetter {

    public ReplyKeyboardMarkup getKeyboard()
    {
        ReplyKeyboardMarkup result = new ReplyKeyboardMarkup(List.of
                (
                        new KeyboardRow(List.of(
                                new KeyboardButton("💡 Герой"),
                                new KeyboardButton("🎒 Рюкзак")
                        ))
                ));

        result.setResizeKeyboard(true);
        return result;
    }
}

package ru.ilot.ilottower.telegram.keyboard;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckOtterKeyboard {

    public InlineKeyboardMarkup getKeyboard(String photoId) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        InlineKeyboardButton yesButton = new InlineKeyboardButton();
        yesButton.setText("Милота");
        yesButton.setCallbackData(String.format("checking %s %s",
                photoId,
                "true"));

        InlineKeyboardButton noButton = new InlineKeyboardButton();
        noButton.setText(":(");
        noButton.setCallbackData(String.format("checking %s %s",
                photoId,
                "false"));

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(yesButton);
        row.add(noButton);

        rowList.add(row);

        markup.setKeyboard(rowList);

        return markup;
    }

}


package jp.ponkichi.bbgreen.dto.converter;

import jp.ponkichi.bbgreen.dto.element.Password;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

// autoApply = true にすると、Entityでこの型を使うだけで自動適用されます
@Converter(autoApply = true)
public class PasswordEncodedConverter implements AttributeConverter<Password.Encoded, String> {

    // オブジェクトからDBの文字列へ変換
    @Override
    public String convertToDatabaseColumn(Password.Encoded attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.value(); // レコードから文字列を取り出す
    }

    // DBの文字列からオブジェクトへ変換
    @Override
    public Password.Encoded convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        return new Password.Encoded(dbData); // 文字列からレコードを復元
    }
}

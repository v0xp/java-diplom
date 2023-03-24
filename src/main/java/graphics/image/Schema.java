package graphics.image;

public class Schema implements TextColorSchema {
    @Override
    public char convert(int color) {
        char r = 0;
        if (0 <= color && color <= 31) {
            r = '#';
        } else if (32 <= color && color <= 64) {
            r = '$';
        } else if (65 <= color && color <= 97) {
            r = '@';
        } else if (98 <= color && color <= 130) {
            r = '%';
        } else if (131 <= color && color <= 163) {
            r = '*';
        } else if (164 <= color && color <= 196) {
            r = '+';
        } else if (197 <= color && color <= 229) {
            r = '-';
        } else if (230 <= color && color <= 255) {
            r = '`';
        }
        return r;
    }
}
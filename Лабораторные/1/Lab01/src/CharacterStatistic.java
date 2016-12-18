/**
 * @author vladislav.trofimov@emc.com
 */
class CharacterStatistic {

    private final char character;
    private int value;

    {
        value = 0;
    }

    public CharacterStatistic(char character) {
        this.character = character;
    }

    public void increase() {
        value++;
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof CharacterStatistic)) return false;

        CharacterStatistic that = (CharacterStatistic) o;

        return character == that.character;
    }

    @Override
    public int hashCode() {
        return (int) character;
    }

    public int compare(final CharacterStatistic right) {
        return Integer.compare(this.value, right.value);
    }
}

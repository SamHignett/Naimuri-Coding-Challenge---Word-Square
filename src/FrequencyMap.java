public class FrequencyMap {

    private final int[] map;

    public FrequencyMap(char[] letters)
    {
        map = new int[26];

        for(char letter : letters)
        {
            map[Character.toUpperCase(letter) - 'A']++;
        }
    }

    public FrequencyMap(int[] clone)
    {
        map = clone;
    }

    public int[] GetMap()
    {
        return map;
    }

    public boolean Contains(String word)
    {
        var tempMap = map.clone();
        for(char letter : word.toCharArray())
        {
            if (tempMap[Character.toUpperCase(letter) - 'A'] == 0)
                return false;
            tempMap[Character.toUpperCase(letter) - 'A']--;
        }

        return true;
    }

    public FrequencyMap Clone()
    {
        return new FrequencyMap(map.clone());
    }

    public void Remove(String word)
    {
        for(char letter : word.toCharArray())
        {
            map[Character.toUpperCase(letter) - 'A']--;
        }
    }
}

package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Patrick on 07.01.2015.
 */
public class ColumnTranspositionDecrypter
{
    public static Map<Integer, List<String>> Decrypt(String cipherText, String knownWord)
    {
        Integer maxBlockLength = cipherText.length();

        Map<Integer, List<String>> overallResults = new HashMap<>();

        for (int blockLength = 1; blockLength <= knownWord.length() + 2; blockLength++)
        {
            // Teilbarkeit muss gegeben sein, da String aufgefüllt wurde
            if (maxBlockLength % blockLength != 0)
            {
                continue;
            }

            String[] cipherTextBlocks = ReverseReadColumnWise(cipherText, maxBlockLength, blockLength);

            Integer maxKnownWordBlockRange = ((knownWord.length() - 2) / blockLength) + 2;
            List<Map<Integer, Integer>> usedChars = new ArrayList<>();

            for (int i = 0; i < cipherTextBlocks.length; i++)
            {
                usedChars.add(new HashMap<>());
            }

            List<List<Map<Integer, Integer>>> foundCharsProtocol = new ArrayList<>();

            for (int startingBlock = 0; startingBlock < cipherTextBlocks.length; startingBlock++)
            {
                FindKnownWord(cipherTextBlocks, startingBlock, knownWord, 0, 1, maxKnownWordBlockRange, usedChars, foundCharsProtocol);
            }

            for (Integer[] sigma : GetSigmas(blockLength, foundCharsProtocol))
            {
                String[] decryptedBlocks = StringHelper.ShuffleStringBlocks(sigma, cipherTextBlocks);

                StringBuilder builder = new StringBuilder();
                for (String s : decryptedBlocks)
                {
                    builder.append(s);
                }

                if (overallResults.containsKey(blockLength))
                {
                    overallResults.get(blockLength).add(builder.toString());
                }
                else
                {
                    overallResults.put(blockLength, new ArrayList<String>()
                    {{
                            add(builder.toString());
                        }});
                }
            }
        }

        return overallResults;
    }

    private static String[] ReverseReadColumnWise(String cipherText, Integer maxBlockLength, int blockLength)
    {
        String[] cipherTextBlocks = new String[maxBlockLength / blockLength];
        for (int i = 0; i < cipherTextBlocks.length; i++)
        {
            cipherTextBlocks[i] = "";
        }

        for (int i = 0; i < cipherText.length(); i++)
        {
            Integer column = i % (maxBlockLength / blockLength);
            char c = cipherText.charAt(i);
            cipherTextBlocks[column] += c;
        }
        return cipherTextBlocks;
    }

    private static List<Integer[]> GetSigmas(int blockLength, List<List<Map<Integer, Integer>>> foundCharsProtocol)
    {
        List<Integer[]> sigmas = new ArrayList<>();

        for (List<Map<Integer, Integer>> foundCharsBlockProtocol : foundCharsProtocol)
        {
            // Entferne alle Blöcke, in denen kein Teil des Know-Words gefunden wurde
            foundCharsBlockProtocol.removeIf(x -> x.size() == 0);

            // Überprüfen, ob in jedem Block an jeder Postion die gleiche Vertauschung protokolliert wurde
            Boolean isCorrect = true;

            chars:
            for (int charPosition = 0; charPosition < blockLength; charPosition++)
            {
                Integer currentValue = -1;

                for (Map<Integer, Integer> charMapping : foundCharsBlockProtocol)
                {
                    // Finde ersten
                    if (charMapping.containsKey(charPosition) && currentValue == -1)
                    {
                        currentValue = charMapping.get(charPosition);
                        continue;
                    }

                    if (charMapping.containsKey(charPosition) && currentValue != -1 && charMapping.get(charPosition) != currentValue)
                    {
                        isCorrect = false;
                        break chars;
                    }
                }
            }

            // Wenn Vertauschungen überall gleich, kann Sigma bestimmt werden
            if (isCorrect)
            {
                Integer[] sigma = new Integer[blockLength];

                for (Map<Integer, Integer> charMapping : foundCharsBlockProtocol)
                {
                    for (int i = 0; i < blockLength; i++)
                    {
                        if (charMapping.containsKey(i))
                        {
                            sigma[i] = charMapping.get(i);
                        }
                    }
                }

                // Ist Sigma vollständig, oder fehlen Zuordununge (max. 2)
                if (IsSigmaComplete(sigma))
                {
                    sigmas.add(sigma);
                }
                else
                {
                    sigmas.addAll(GetFilledSigmas(sigma));
                }
            }
        }

        return sigmas;
    }

    private static List<Integer[]> GetFilledSigmas(Integer[] sigma)
    {

        // Finde offene Postionen und Nummern
        List<Integer> openPositions = IntStream.rangeClosed(0, sigma.length - 1).boxed().collect(Collectors.toList());
        List<Integer> openNumbers = IntStream.rangeClosed(0, sigma.length - 1).boxed().collect(Collectors.toList());

        for (Integer i = 0; i < sigma.length; i++)
        {
            if (sigma[i] != null)
            {
                openPositions.remove(i);
                openNumbers.remove(sigma[i]);
            }
        }

        // Fülle offene Positionen mit Permutationen der Nummern
        List<Integer[]> filledSigmas = new ArrayList<>();

        for (int rank = 0; rank < MathHelper.factorial(openNumbers.size()); rank++)
        {
            Integer[] perm = ArrayHelper.unrank(openNumbers.toArray(new Integer[openNumbers.size()]), rank);

            for (int i = 0; i < perm.length; i++)
            {
                sigma[openPositions.get(i)] = perm[i];
            }

            filledSigmas.add(sigma.clone());
        }

        return filledSigmas;
    }


    private static boolean IsSigmaComplete(Integer[] sigma)
    {
        for (int i = 0; i < sigma.length; i++)
        {
            if (sigma[i] == null)
            {
                return false;
            }
        }

        return true;
    }

    private static Boolean FindKnownWord(String[] cipherTextBlocks, Integer currentBlock, String knownWord, Integer currentKnownWordCharPosition,
                                         Integer currentKnownWordBlockRange, Integer maxKnownWordBlockRange, List<Map<Integer, Integer>> usedChars, List<List<Map<Integer, Integer>>> result)
    {
        if (currentKnownWordCharPosition == knownWord.length())
        {
            // Add Ergebnis
            result.add(Clone(usedChars));
            return true;
        }

        int blockLength = cipherTextBlocks[currentBlock].length();
        Character currentChar = knownWord.charAt(currentKnownWordCharPosition);

        for (int charPosition = 0; charPosition < blockLength; charPosition++)
        {
            // Char an dieser Position wurde schon benutzt
            if (usedChars.get(currentBlock).containsKey(charPosition))
            {
                continue;
            }

            if (cipherTextBlocks[currentBlock].charAt(charPosition) == currentChar)
            {
                usedChars.get(currentBlock).put(charPosition, (currentKnownWordCharPosition + 1) % blockLength);
                Boolean isknownWordFound = FindKnownWord(cipherTextBlocks, currentBlock, knownWord, currentKnownWordCharPosition + 1, currentKnownWordBlockRange, maxKnownWordBlockRange, usedChars, result);

                if (currentKnownWordBlockRange < maxKnownWordBlockRange && currentBlock + 1 < cipherTextBlocks.length && !isknownWordFound)
                {
                    // Gehe evtl. eine Block weiter
                    if (currentKnownWordBlockRange == 1 || (currentKnownWordBlockRange > 1 && usedChars.get(currentBlock).size() == blockLength))
                    {
                        FindKnownWord(cipherTextBlocks, currentBlock + 1, knownWord, currentKnownWordCharPosition + 1, currentKnownWordBlockRange + 1, maxKnownWordBlockRange, usedChars, result);
                    }
                }

                usedChars.get(currentBlock).remove(charPosition);
            }
        }

        return false;
    }

    private static <T, Z> List<Map<T, Z>> Clone(List<Map<T, Z>> toClone)
    {
        List<Map<T, Z>> copy = new ArrayList<>();

        for (Map<T, Z> m : toClone)
        {
            copy.add(new HashMap<>(m));
        }

        return copy;
    }
}

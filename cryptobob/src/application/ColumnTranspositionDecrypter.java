package application;

import application.helper.ArrayHelper;
import application.helper.MathHelper;
import application.helper.StringHelper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ColumnTranspositionDecrypter
{
    public static Map<Integer, List<String>> Decrypt(String cipherText, String knownWord)
    {
        Integer maxBlockLength = cipherText.length();

        Map<Integer, List<String>> overallResults = new HashMap<>();

        // probiere jede Blocklaenge aus
        // aufhoeren nach kown-word Laenge + 2, um nicht zu viele moegliche sigmas zu haben
        for (int blockLength = 1; blockLength <= knownWord.length() + 2; blockLength++)
        {
            // Teilbarkeit muss gegeben sein, da String aufgefüllt wurde
            if (maxBlockLength % blockLength != 0)
            {
                continue;
            }

            String[] cipherTextBlocks = ReverseReadColumnWise(cipherText, maxBlockLength, blockLength);

            // ueber wieviele Bloecke sich das Wort max. erstrecken kann
            Integer maxKnownWordBlockRange = ((knownWord.length() - 2) / blockLength) + 2;

            List<Map<Integer, Integer>> usedChars = new ArrayList<>();
            for (int i = 0; i < cipherTextBlocks.length; i++)
            {
                usedChars.add(new HashMap<>());
            }

            List<List<Map<Integer, Integer>>> foundCharsProtocol = new ArrayList<>();

            for (int startingBlock = 0; startingBlock < cipherTextBlocks.length; startingBlock++)
            {
                // Hokus-Pokus, simsalabim
                FindKnownWord(cipherTextBlocks, startingBlock, knownWord, 0, 1, maxKnownWordBlockRange, usedChars, foundCharsProtocol);
            }

            // mögliche Sigmas auswerten
            // es kann mehrere Loesungen zu einer Blocklaenge geben
            for (Integer[] sigma : GetSigmas(blockLength, foundCharsProtocol))
            {
                ArrayHelper.print(sigma);
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

    private static Set<Integer[]> GetSigmas(int blockLength, List<List<Map<Integer, Integer>>> foundCharsProtocol)
    {
        Set<Integer[]> sigmas = new TreeSet<Integer[]>(new Comparator<Integer[]>()
        {
            @Override
            public int compare(Integer[] o1, Integer[] o2)
            {
                return Arrays.equals(o1, o2) ? 0 : 1;
            }
        });

        // Iteriere ueber alle Protokolle
        protocols:
        for (List<Map<Integer, Integer>> blockProtocols : foundCharsProtocol)
        {
            // Entferne alle Bloecke, in denen kein Teil des Know-Words gefunden wurde
            blockProtocols.removeIf(x -> x.size() == 0);

            Integer[] sigma = new Integer[blockLength];
            blockLengths:
            for (int positionInKnownWord = 0; positionInKnownWord < blockLength; positionInKnownWord++)
            {
                for (int currentBlock = 0; currentBlock < blockProtocols.size(); currentBlock++)
                {
                    Map<Integer, Integer> charMapping = blockProtocols.get(currentBlock);

                    for (Map.Entry<Integer, Integer> entry : charMapping.entrySet())
                    {
                        // Char für positionInKnownWord gefunden?
                        if (entry.getValue() != positionInKnownWord)
                        {
                            continue;
                        }

                        // Sigma-Stelle breits vergeben? Wenn ja, dann weiter zum naechsten Protokoll
                        if (sigma[entry.getKey()] != null)
                        {
                            continue protocols;
                        }

                        if (currentBlock == 0)
                        {
                            // beim ersten Block von hinten auffuellen
                            sigma[entry.getKey()] = blockLength - charMapping.size() + positionInKnownWord;
                        }
                        else
                        {
                            // ansonsten von vorn auffuellen
                            sigma[entry.getKey()] = positionInKnownWord - blockProtocols.get(currentBlock - 1).size();
                        }

                        continue blockLengths;
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

        int blockLength = cipherTextBlocks[currentBlock].length();

        if (currentKnownWordCharPosition == knownWord.length())
        {
            // Add Ergebnis
            result.add(Clone(usedChars));
            return true;
        }

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
                usedChars.get(currentBlock).put(charPosition, currentKnownWordCharPosition);
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

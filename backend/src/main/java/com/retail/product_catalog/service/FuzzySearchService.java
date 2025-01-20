package com.retail.product_catalog.service;

import org.springframework.stereotype.Service;

@Service
public class FuzzySearchService {

    public boolean isSimilar(String source, String target) {
        if (source == null || target == null) {
            return false;
        }

        source = source.toLowerCase();
        target = target.toLowerCase();

        if (source.contains(target) || target.contains(source)) {
            return true;
        }

        int matchingChars = findMatchingCharacters(source, target);

        int minLength = Math.min(source.length(), target.length());
        int requiredMatches = minLength / 2;

        return matchingChars >= requiredMatches;
    }

    /**
     * Counts how many characters match in sequence between two strings
     */
    private int findMatchingCharacters(String str1, String str2) {
        int matching = 0;
        int index1 = 0;
        int index2 = 0;

        while (index1 < str1.length() && index2 < str2.length()) {
            if (str1.charAt(index1) == str2.charAt(index2)) {
                matching++;
                index1++;
                index2++;
            } else if (index2 < str2.length() - 1 && str1.charAt(index1) == str2.charAt(index2 + 1)) {
                index2++;
            } else if (index1 < str1.length() - 1 && str1.charAt(index1 + 1) == str2.charAt(index2)) {
                index1++;
            } else {
                index1++;
                index2++;
            }
        }

        return matching;
    }
}

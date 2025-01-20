package com.retail.product_catalog.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;

class FuzzySearchServiceTest {

    private FuzzySearchService fuzzySearchService;

    @BeforeEach
    void setUp() {
        fuzzySearchService = new FuzzySearchService();
    }

    @Nested
    @DisplayName("Fuzzy Search Tests")
    class FuzzySearchTests {

        @ParameterizedTest
        @DisplayName("Should match similar strings")
        @CsvSource({
                "iPhone,iphone,true",
                "laptop,labtop,true",
                "coffee,cofee,true",
                "Samsung TV,samsung,true",
                "MacBook Pro,macbook,true",
                "completely different,not matching,false",
                "short,very long string,false"
        })
        void shouldMatchSimilarStrings(String source, String target, boolean expected) {
            boolean result = fuzzySearchService.isSimilar(source, target);
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("Should match case-insensitive strings")
        void shouldMatchCaseInsensitiveStrings() {
            assertThat(fuzzySearchService.isSimilar("IPHONE", "iphone")).isTrue();
            assertThat(fuzzySearchService.isSimilar("MacBook", "macbook")).isTrue();
            assertThat(fuzzySearchService.isSimilar("Samsung", "SAMSUNG")).isTrue();
        }

        @Test
        @DisplayName("Should match substring matches")
        void shouldMatchSubstrings() {
            assertThat(fuzzySearchService.isSimilar("iPhone 13 Pro", "iphone")).isTrue();
            assertThat(fuzzySearchService.isSimilar("Samsung TV 4K", "samsung tv")).isTrue();
            assertThat(fuzzySearchService.isSimilar("MacBook Pro M1", "macbook")).isTrue();
        }

        @ParameterizedTest
        @DisplayName("Should handle null and empty inputs")
        @NullAndEmptySource
        void shouldHandleNullAndEmptyInputs(String input) {
            assertThat(fuzzySearchService.isSimilar(input, "test")).isFalse();
            assertThat(fuzzySearchService.isSimilar("test", input)).isFalse();
        }

        @Test
        @DisplayName("Should handle special characters")
        void shouldHandleSpecialCharacters() {
            assertThat(fuzzySearchService.isSimilar("iPhone-13", "iphone 13")).isTrue();
            assertThat(fuzzySearchService.isSimilar("Samsung.TV", "samsung tv")).isTrue();
            assertThat(fuzzySearchService.isSimilar("Mac&Book", "macbook")).isTrue();
        }
    }

    @Nested
    @DisplayName("Character Matching Tests")
    class CharacterMatchingTests {

        @Test
        @DisplayName("Should match exact strings")
        void shouldMatchExactStrings() {
            assertThat(fuzzySearchService.isSimilar("exact", "exact")).isTrue();
        }

        @Test
        @DisplayName("Should handle single character differences")
        void shouldHandleSingleCharacterDifferences() {
            assertThat(fuzzySearchService.isSimilar("apple", "aple")).isTrue();
            assertThat(fuzzySearchService.isSimilar("phone", "fone")).isTrue();
        }

        @Test
        @DisplayName("Should handle character transpositions")
        void shouldHandleCharacterTranspositions() {
            assertThat(fuzzySearchService.isSimilar("laptop", "labtop")).isTrue();
            assertThat(fuzzySearchService.isSimilar("recieve", "receive")).isTrue();
        }
    }
}

// External AI service integration in the Frameworks & Drivers layer.
// Archie

package frameworks_and_drivers.AIServices;

import java.net.http.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class GeminiApiClient {

    private final String apiKey;
    private static final String API_URL = "404";

    // Init GeminiApiClient with API key via constructor
    // @param apiKey The API key for authenticating with the Gemini service.
    public GeminiApiClient(String apiKey) {
        this.apiKey = apiKey;
    }

    // SCRAPPED: Send a request to the Gemini API to generate a study card based on the provided topic and source text.
    // @param topic The topic for the generated card.
    // @param sourceText The source text to base the card on.
    // @return The raw response from the Gemini API, or null if the request fails.
    public String generateCard(String topic, String sourceText) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    // SCRAPPED: Format the prompt sent to the Gemini API to ensure proper card structure.
    // @param topic The topic for the generated card.
    // @param sourceText The source text to base the card on.
    // @return A properly formatted prompt string for the Gemini API.
    private String formatPrompt(String topic, String sourceText) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    // SCRAPPED: Make the actual HTTP request to the Gemini API.
    // @param requestBody The JSON request body to send to the API.
    // @return The raw response from the Gemini API, or null if the request fails.
    private String makeApiRequest(String requestBody) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    // SCRAPPED: Validate that the API key is properly formatted and valid.
    // @return True if the API key is valid, false otherwise.
    private boolean validateApiKey() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    // SCRAPPED: Handle API response errors and return appropriate error messages.
    // @param errorCode The HTTP error code from the API response.
    // @param errorMessage The error message from the API response.
    // @return A formatted error message suitable for application use.
    private String handleApiError(int errorCode, String errorMessage) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    // SCRAPPED: Check if the API request is within rate limits.
    // @return True if the request can be made, false if rate limited.
    private boolean checkRateLimit() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }
}
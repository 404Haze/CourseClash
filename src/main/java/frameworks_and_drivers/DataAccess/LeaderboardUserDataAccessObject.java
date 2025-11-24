package frameworks_and_drivers.DataAccess;

import entity.User;
import interface_adapter.user_session.UserSession;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.DataAccessException;
import use_case.leaderboard.LeaderboardType;
import use_case.leaderboard.LeaderboardUserDataAccessInterface;

import java.util.*;

import static frameworks_and_drivers.DataAccess.StaticMethods.makeApiRequest;

public class LeaderboardUserDataAccessObject implements LeaderboardUserDataAccessInterface {

    private UserSession session = null;
    public LeaderboardUserDataAccessObject(UserSession userSession) {
        this.session = userSession;
    }

    public LeaderboardUserDataAccessObject() {

    }

    @Override
    public Map<LeaderboardType, ArrayList<User>> getTopUsers(int topN) throws DataAccessException {
        final String method = "/api/get-top-user";
        HashMap<String, String> params = new HashMap<>();
        params.put("top-n", String.valueOf(topN));
        Map<LeaderboardType, ArrayList<User>> result = new HashMap<>();

        for (LeaderboardType x : LeaderboardType.values()) {
            String type = x.name();
            result.put(x, new ArrayList<>());
            params.put("type", type);
            JSONObject response = makeApiRequest("GET", method, params, session.getApiKey());
            boolean ascOrder = response.getBoolean("asc-order");
            JSONArray rank = response.getJSONArray("rank");
            for (int i = 0; i < rank.length(); i++) {
                JSONObject cu = rank.getJSONObject(i);
                User t = new User(cu.getString("username"), null);
                t.setLevel(cu.optInt("level", 0));
                t.setExperiencePoints(cu.optInt("points", 0));
                t.setQuestionsCorrect(cu.optInt("correct", 0));
                t.setQuestionsAnswered(cu.optInt("answered", 0));
                result.get(x).add(t);
            }
            if (ascOrder) {
                Collections.reverse(result.get(x));
            }
        }
        return result;
    }

}

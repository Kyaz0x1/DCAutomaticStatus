package net.kyaz0x1.dcautomaticstatus.api;

import com.google.gson.Gson;
import net.kyaz0x1.dcautomaticstatus.api.except.StatusErrorException;
import net.kyaz0x1.dcautomaticstatus.api.http.HttpResponseCode;
import net.kyaz0x1.dcautomaticstatus.api.models.CustomStatus;
import net.kyaz0x1.dcautomaticstatus.api.models.DiscordError;
import net.kyaz0x1.dcautomaticstatus.api.models.Status;
import net.kyaz0x1.dcautomaticstatus.api.service.WebService;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public final class DiscordAPI {

    private static DiscordAPI INSTANCE;

    private final WebService webService;
    private final Gson gson;

    private final String API_ENDPOINT = "https://discord.com/api/v9";

    private DiscordAPI(){
        this.webService = WebService.getInstance();
        this.gson = new Gson();
    }

    public boolean updateStatus(Status status) throws StatusErrorException {
        final String url = API_ENDPOINT + "/users/@me/settings";
        final String json = gson.toJson(new CustomStatus(status));

        final String response = webService.patch(url, json);
        if(response.contains("custom_status"))
            return true;

        final DiscordError error = gson.fromJson(response, DiscordError.class);
        throw new StatusErrorException(error.getMessage());
    }

    public boolean isValidAccount(String token){
        final String url = "https://discord.com/api/v9/users/@me";
        final Request request = new Request.Builder()
                .url(url)
                .header("Authorization", token)
                .build();
        try {
            final Response response = webService.getClient().newCall(request).execute();
            return response.code() == HttpResponseCode.OK.getCode();
        }catch(IOException e) {
            return false;
        }
    }

    public static DiscordAPI getInstance(){
        if(INSTANCE == null){
            synchronized(DiscordAPI.class){
                if(INSTANCE == null){
                    INSTANCE = new DiscordAPI();
                }
            }
        }
        return INSTANCE;
    }

}
package tk.tnicy.tradislation.utiles;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.LitePal;
import tk.tnicy.tradislation.R;
import tk.tnicy.tradislation.entities.BigType;
import tk.tnicy.tradislation.entities.SmallType;
import tk.tnicy.tradislation.entities.Translation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class Utility {
    private static final String TAG = "handleJson";


    public static List<Translation> queryAllTranslations(final Activity activity) {
        List<Translation> translations = LitePal.findAll(Translation.class);

        String address = activity.getString(R.string.springPort);
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Toast.makeText(getContext(), "Translation获取失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (handleTranslationJson(response.body().string())) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            queryAllTranslations(activity);
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Translation Handle 失败", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return translations;
    }


    private static boolean handleTranslationJson(String response) {


        if (!TextUtils.isEmpty(response)) {
            try {


                JSONArray allTranslations = new JSONArray(response);
                for (int i = 0; i < allTranslations.length(); i++) {
                    JSONObject translationsJSONObject = allTranslations.getJSONObject(i);
                    Translation translation = new Translation();
                    translation.setTranslationId(translationsJSONObject.getInt("translationId"));
                    translation.setBigTypeId(translationsJSONObject.getInt("bigTypeId"));
                    translation.setSmallTypeId(translationsJSONObject.getInt("smallTypeId"));
                    translation.setChi(translationsJSONObject.getString("chi"));
                    translation.setEng(translationsJSONObject.getString("eng"));
                    translation.setBigType(translationsJSONObject.getString("bigType"));
                    translation.setSmallType(translationsJSONObject.getString("smallType"));
                    translation.setDetail(translationsJSONObject.getString("detail"));
                    translation.setRelated(translationsJSONObject.getString("related"));
                    translation.setSpelling(translationsJSONObject.getString("spelling"));

                    if (LitePal.where("translationid = ?", translation.getTranslationId().toString()).find(Translation.class).size() == 0) {
                        translation.save();
                    }else {
                        translation.update(LitePal.where("translationid = ?", translation.getTranslationId().toString()).find(Translation.class).get(0).getId());
                    }


                }


                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public static List<BigType> queryAllBigTypes(final Activity activity) {
        List<BigType> bigTypes = LitePal.findAll(BigType.class);


        if (bigTypes.size() == 0) {
            String address = "http://101.132.120.236:9999/bigType";
            HttpUtil.sendOkHttpRequest(address, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    Toast.makeText(getContext(), "BigTypes获取失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (handleBigTypeJson(response.body().string())) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                queryAllBigTypes(activity);
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "BigType Handle 失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return bigTypes;

    }


    private static boolean handleBigTypeJson(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allBigTypes = new JSONArray(response);
                for (int i = 0; i < allBigTypes.length(); i++) {
                    JSONObject bigTypeJSONObject = allBigTypes.getJSONObject(i);
                    BigType bigType = new BigType();
                    bigType.setBigTypeId(bigTypeJSONObject.getInt("bigTypeId"));
                    bigType.setTypeName(bigTypeJSONObject.getString("typeName"));

                    if (LitePal.where("bigTypeid = ?", bigType.getBigTypeId().toString()).find(BigType.class).size()==0) {

                        bigType.save();
                    }else {
                        bigType.update(LitePal.where("bigTypeid = ?", bigType.getBigTypeId().toString()).find(BigType.class).get(0).getId());
                    }
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    public static List<SmallType> queryAllSmallTypes(final Activity activity) {
        List<SmallType> smallTypes = LitePal.findAll(SmallType.class);


        if (smallTypes.size() == 0) {
            String address = activity.getString(R.string.springPort) + "smallType";
            HttpUtil.sendOkHttpRequest(address, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    Toast.makeText(getContext(), "SmallTypes获取失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (handleSmallTypeJson(response.body().string())) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                queryAllSmallTypes(activity);
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "SmallType Handle 失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return smallTypes;

    }


    private static boolean handleSmallTypeJson(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allSmallTypes = new JSONArray(response);
                for (int i = 0; i < allSmallTypes.length(); i++) {
                    JSONObject smallTypesJSONObject = allSmallTypes.getJSONObject(i);
                    SmallType smallType = new SmallType();
                    smallType.setSmallTypeId(smallTypesJSONObject.getInt("smallTypeId"));
                    smallType.setTypeName(smallTypesJSONObject.getString("typeName"));
                    smallType.setBigTypeId(smallTypesJSONObject.getInt("bigTypeId"));
                    if (LitePal.where("smallTypeid = ?", smallType.getSmallTypeId().toString()).find(SmallType.class).size() == 0) {
                        smallType.save();
                    }else {
                        smallType.update(LitePal.where("smallTypeid = ?", smallType.getSmallTypeId().toString()).find(SmallType.class).get(0).getId());
                    }
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    public static Boolean reformTypesData() {
        List<Translation> translations = LitePal.findAll(Translation.class);

        LitePal.deleteAll(Translation.class);
        LitePal.deleteAll(BigType.class);
        LitePal.deleteAll(SmallType.class);

        List<BigType> bigTypes = new ArrayList<>();
        List<SmallType> smallTypes = new ArrayList<>();

        for (Translation translation :
                translations) {

            boolean bigFlag = false;
            for (BigType bigType : bigTypes) {
                if (translation.getBigType().equals(bigType.getTypeName())) {
                    bigFlag = true;

                    boolean smallFlag = false;
                    for (SmallType smallType :
                            smallTypes) {
                        if (translation.getSmallType().equals(smallType.getTypeName())) {
                            smallFlag = true;
                        }
                    }
                    if (!smallFlag) {
                        SmallType smallType = new SmallType();
                        smallType.setTypeName(translation.getSmallType());
                        smallType.setBigTypeId(bigType.getId());
                        smallType.save();  //SAVE
                        smallTypes.clear();
                        smallTypes = LitePal.findAll(SmallType.class);
                    }


                }
            }
            if (!bigFlag) {
                BigType bigType = new BigType();
                bigType.setTypeName(translation.getBigType());
                bigType.save();   //SAVE
                bigTypes.clear();
                bigTypes = LitePal.findAll(BigType.class);


                SmallType smallType = new SmallType();
                smallType.setTypeName(translation.getSmallType());
                smallType.setBigTypeId(LitePal.where("typename is ?", translation.getBigType()).find(BigType.class).get(0).getId());
                smallType.save();   //SAVE
                smallTypes.clear();
                smallTypes = LitePal.findAll(SmallType.class);

            }


        }

        bigTypes.clear();
        bigTypes = LitePal.findAll(BigType.class);
        smallTypes.clear();
        smallTypes = LitePal.findAll(SmallType.class);

        for (Translation translation : translations) {
            boolean bigFlag = false;
            for (BigType bigType :
                    bigTypes) {
                if (bigType.getTypeName().equals(translation.getBigType())) {
                    translation.setBigTypeId(bigType.getId());

                    bigFlag = true;


                    boolean smallFlag = false;
                    for (SmallType smallType :
                            smallTypes) {
                        if (translation.getSmallType().equals(smallType.getTypeName())) {
                            translation.setSmallTypeId(smallType.getId());
                            smallFlag = true;

                            translation.save();  //SAVE

                        }
                    }

                    if (!smallFlag) {
                        return false;
                    }
                }
            }

            if (!bigFlag) {
                return false;
            }
        }


        return true;
    }


}
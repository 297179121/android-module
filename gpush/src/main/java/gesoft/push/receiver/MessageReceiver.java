package gesoft.push.receiver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import gesoft.push.GPushConstant;
import gesoft.push.common.NotificationService;
import gesoft.push.po.GMessage;
import gesoft.push.po.XGNotification;

public class MessageReceiver extends XGPushBaseReceiver {
	private Intent intent = new Intent(GPushConstant.XG_NOTIFACTION_SHOW_RECEIVER);
	public static final String LogTag = "TPushReceiver";

	private void show(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	// 通知展示
	@Override
	public void onNotifactionShowedResult(Context context, XGPushShowedResult notifiShowedRlt) {
		if (context == null || notifiShowedRlt == null) {
			return;
		}
		/*XGNotification notific = new XGNotification();
		notific.setMsg_id(notifiShowedRlt.getMsgId());
		notific.setTitle(notifiShowedRlt.getTitle());
		notific.setContent(notifiShowedRlt.getContent());
		// notificationActionType==1为Activity，2为url，3为intent
		notific.setNotificationActionType(notifiShowedRlt
				.getNotificationActionType());
		// Activity,url,intent都可以通过getActivity()获得
		notific.setActivity(notifiShowedRlt.getActivity());
		notific.setUpdate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime()));
		NotificationService.getInstance(context).save(notific);
		show(context, "您有1条新消息, " + "通知被展示 ， " + notifiShowedRlt.toString());*/
		context.sendBroadcast(intent);
	}

	@Override
	public void onUnregisterResult(Context context, int errorCode) {
		if (context == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "反注册成功";
		} else {
			text = "反注册失败" + errorCode;
		}
		Log.d(LogTag, text);
		show(context, text);

	}

	@Override
	public void onSetTagResult(Context context, int errorCode, String tagName) {
		if (context == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "\"" + tagName + "\"设置成功";
		} else {
			text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
		}
		Log.d(LogTag, text);
		show(context, text);

	}

	@Override
	public void onDeleteTagResult(Context context, int errorCode, String tagName) {
		if (context == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "\"" + tagName + "\"删除成功";
		} else {
			text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
		}
		Log.d(LogTag, text);
		show(context, text);

	}

	// 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
	@Override
	public void onNotifactionClickedResult(Context context, XGPushClickedResult message) {
		if (context == null || message == null) {
			return;
		}

		if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
			if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {

				GMessage msg = new GMessage();
				msg.setActionType(message.getActionType());
				msg.setContent(message.getContent());
				msg.setCustomContent(message.getCustomContent());
				msg.setMsgId(message.getMsgId());
				msg.setTitle(message.getTitle());


				/*
				//启动FirstActivity
				Intent intentFirst = new Intent();
				ComponentName componentName= new ComponentName( "gesoft.gandroid", "gesoft.gandroid.FirstActivity");
				intentFirst.setComponent(componentName);
				intentFirst.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				//启动SecondActivity
				Intent intentSecond = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable(GPushConstant.XG_BUNDLE_GMESSAGE, msg);
				componentName= new ComponentName( "gesoft.gandroid", "gesoft.gandroid.SecondActivity");
				intentSecond.setComponent(componentName);
				intentSecond.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intentSecond.putExtra(GPushConstant.XG_INTENT_BUNDLE, bundle);

				Intent[] intents = {intentFirst, intentSecond};

				context.startActivities(intents);*/
			}
		} else if (message.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
			// 通知被清除啦。。。。
			// APP自己处理通知被清除后的相关动作
		}
	}

	@Override
	public void onRegisterResult(Context context, int errorCode,
								 XGPushRegisterResult message) {
		// TODO Auto-generated method stub
		if (context == null || message == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = message + "注册成功";
			// 在这里拿token
			String token = message.getToken();
		} else {
			text = message + "注册失败，错误码：" + errorCode;
		}
		Log.d(LogTag, text);
		show(context, text);
	}

	// 消息透传
	@Override
	public void onTextMessage(Context context, XGPushTextMessage message) {
		// TODO Auto-generated method stub
		String text = "收到消息:" + message.toString();
		// 获取自定义key-value
		String customContent = message.getCustomContent();
		if (customContent != null && customContent.length() != 0) {
			try {
				JSONObject obj = new JSONObject(customContent);
				// key1为前台配置的key
				if (!obj.isNull("key")) {
					String value = obj.getString("key");
					Log.d(LogTag, "get custom value:" + value);
				}
				// ...
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// APP自主处理消息的过程...
		Log.d(LogTag, text);
		show(context, text);
	}

}
/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package org.apache.cordova.audiofilechooser;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import android.content.Context;
import org.apache.cordova.PluginResult;
import android.util.Log;
import android.content.Intent;
import android.app.Activity;
import android.net.Uri;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class provides access to vibration on the device.
 */
public class AudioFileChooser extends CordovaPlugin {
	private CallbackContext callbackContext = null;
    /**
     * Constructor.
     */
    public AudioFileChooser() {
    Log.d("customPlugin", " selectAudioFile ");
    }

    @Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		this.callbackContext = callbackContext;
    	if (action.equals("selectAudioFile")) { 
        	Log.d("customPlugin", " selectAudioFile ");
        	
        	Runnable selectAudioFile = new Runnable() {

                    @Override
                    public void run() {
                    Intent intent = new Intent(Intent.ACTION_PICK ,android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI); 
    	cordova.setActivityResultCallback(AudioFileChooser.this);
    	cordova.getActivity().startActivityForResult(Intent.createChooser(intent, "Gallery"), 6);
                    }
                };
        	this.cordova.getActivity().runOnUiThread(selectAudioFile);
        	return true;
    	}
   		else {
        	return false;
   	 	}
	}
				
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
     	Log.d("customPlugin", "Calling onActivityResult");
    	if (resultCode == Activity.RESULT_OK && requestCode == 6)
    	{
        	Uri selectedAudioUri = data.getData();
						
       	 	Log.d("customPlugin", "I picked this file " + selectedAudioUri);
       	 	
        	if (selectedAudioUri != null)
        	{
        		PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, selectedAudioUri.toString());
        		pluginResult.setKeepCallback(true);
        		this.callbackContext.sendPluginResult(pluginResult);
        	}
    	}
	}

}

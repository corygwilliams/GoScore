/*
 * Copyright (C) 2011 Cory Williams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.goscore;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;

public class PreviewProcessor implements PreviewCallback {
	
	private CameraOverlay mOverlay;

	PreviewProcessor(CameraOverlay overlay) {
		mOverlay = overlay;
	}
	
	public void onPreviewFrame(byte[] data, Camera camera) {
		Camera.Size previewSize = camera.getParameters().getPreviewSize();
		if (camera.getParameters().getPreviewFormat() != ImageFormat.NV21) {
			Log.e("GoScore", "Bad camera format");
			return;
		}
		
		int rowSize = previewSize.width;
		int boardSize = previewSize.height / 40 * 38;
		Bitmap bitmap = Bitmap.createBitmap(boardSize, boardSize, Bitmap.Config.ARGB_8888);
		int startLeft = rowSize / 2 - boardSize / 2;
		int startTop = previewSize.height / 2 - boardSize / 2;
		int offset;
		for (int row = 0; row < boardSize; ++row) {
			offset = startLeft + (startTop + row) * rowSize;
			for (int column = 0; column < boardSize; ++column) {
				int gray = (data[offset] & 0xFF);
				bitmap.setPixel(column, row, Color.rgb(gray, gray, gray));
				++offset;
			}
		}
		
		mOverlay.setBoardImage(bitmap);
	}

}

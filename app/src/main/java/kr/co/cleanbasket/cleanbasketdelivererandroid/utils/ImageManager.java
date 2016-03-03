package kr.co.cleanbasket.cleanbasketdelivererandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import eu.janmuller.android.simplecropimage.CropImage;

public class ImageManager {
	
	// Device Density
	public static float mDensityMultiplier;
	public static void initDisplayDensity(Context context) {
		mDensityMultiplier = context.getResources().getDisplayMetrics().density;
	}
	
	public static String TEMP_PHOTO_FILE_NAME;
	public static final int REQUEST_CODE_CAMERA = 0x01;
	public static final int REQUEST_CODE_GALLERY = 0x2;
	public static final int REQUEST_CODE_WEB = 0x03;
	public static final int REQUEST_CODE_CROP_IMAGE = 0x4;
	public static final int IMAGE_COVER = 1;
	public static final int IMAGE_PROFIL = 2;
	public static final String IMAGE_PATH = CropImage.IMAGE_PATH;
	
	
	public static void openCamera(Fragment fragment) {
		Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fragment.startActivityForResult(photoPickerIntent, REQUEST_CODE_CAMERA);
	}
	
	public static void openCamera(Activity fragment) {
		Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fragment.startActivityForResult(photoPickerIntent, REQUEST_CODE_CAMERA);
	}
	
	public static void openGallery(Fragment fragment) {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		fragment.startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
	}
	
	public static void openGallery(Activity fragment) {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		fragment.startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
	}
	
	public static void startCropImage(Fragment fragment, File file, int type) {
		Intent intent = new Intent(fragment.getActivity(), CropImage.class);
		intent.putExtra(CropImage.IMAGE_PATH, file.getPath());
		intent.putExtra(CropImage.SCALE, true);

		intent.putExtra(CropImage.ASPECT_X, (type == IMAGE_COVER) ? 3 : 1);
		intent.putExtra(CropImage.ASPECT_Y, (type == IMAGE_COVER) ? 2 : 1);

		fragment.startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
	}
	
	public static void startCropImage(Activity fragment, File file, int type) {
		Intent intent = new Intent(fragment, CropImage.class);
		intent.putExtra(CropImage.IMAGE_PATH, file.getPath());
		intent.putExtra(CropImage.SCALE, true);

		intent.putExtra(CropImage.ASPECT_X, (type == IMAGE_COVER) ? 3 : 1);
		intent.putExtra(CropImage.ASPECT_Y, (type == IMAGE_COVER) ? 2 : 1);

		fragment.startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
	}
	
	public static void startCropImage(Fragment fragment, File file) {
		Intent intent = new Intent(fragment.getActivity(), CropImage.class);
		intent.putExtra(CropImage.IMAGE_PATH, file.getPath());
		intent.putExtra(CropImage.SCALE, true);

		intent.putExtra(CropImage.ASPECT_X, 0);
		intent.putExtra(CropImage.ASPECT_Y, 0);

		fragment.startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
	}
	
	public static void startCropImage(Activity fragment, File file) {
		Intent intent = new Intent(fragment, CropImage.class);
		intent.putExtra(CropImage.IMAGE_PATH, file.getPath());
		intent.putExtra(CropImage.SCALE, true);

		intent.putExtra(CropImage.ASPECT_X, 1);
		intent.putExtra(CropImage.ASPECT_Y, 1);

		fragment.startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
	}
	
	public static void setTempPhotoFileName(int type) {
		switch(type) {
		case IMAGE_COVER:
			TEMP_PHOTO_FILE_NAME = "cover.jpg";
			break;
		case IMAGE_PROFIL:
			TEMP_PHOTO_FILE_NAME = "profil.jpg";
			break;
		}
	}
	public static final String TEMP_FILE_NAME = "tempimage.jpg";
	public static void setTempPhotoFileName(String filename) {
		TEMP_PHOTO_FILE_NAME = filename;
	}
	public static void copyStream(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}
}

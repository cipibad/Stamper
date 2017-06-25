package ro.cipex.android.stamper;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class StamperFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE = 0;
    private static final String TAG = "StamperFragment";


    private DrawingView mDrawingView;

    public static StamperFragment newInstance() {
        return new StamperFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_stamper, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ShapeList sl = ShapeList.get();
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                sl.clear();
                mDrawingView.invalidate();
                return true;
            case R.id.menu_item_undo:
                sl.undo();
                mDrawingView.invalidate();
                return true;
            case R.id.menu_item_share:
                if (weHavePermissionExternalStorageReadWrite()) {
                    shareScreenshot();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareScreenshot() {
        Bitmap b = takeScreenshot();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), b, "Stamp", null);

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("image/*");

        i.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));

        i = Intent.createChooser(i, getString(R.string.share_via));
        startActivity(i);
    }

    public Bitmap takeScreenshot() {

        mDrawingView.buildDrawingCache(true);
        Bitmap b = mDrawingView.getDrawingCache(true).copy(Bitmap.Config.RGB_565, false);
        mDrawingView.destroyDrawingCache();
        return b;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stamper, container, false);

        mDrawingView = (DrawingView) v.findViewById(R.id.drawing_view);

        return v;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult");
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    shareScreenshot();
                }
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }
    }

    boolean weHavePermissionExternalStorageReadWrite() {
        if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
                ) {

            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE);
            return false;
        }
        return true;
    }

}

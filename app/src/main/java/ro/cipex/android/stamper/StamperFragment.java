package ro.cipex.android.stamper;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class StamperFragment extends Fragment {

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

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stamper, container, false);

        mDrawingView = (DrawingView) v.findViewById(R.id.drawing_view);

        return v;
    }

}

package com.cbs.sscbs.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbs.sscbs.DataClass.DataClass;
import com.cbs.sscbs.Others.FullScreenImage;
import com.cbs.sscbs.R;
import com.cbs.sscbs.utils.ItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

//
// import static com.cbs.sscbs.DataClass.CONSTANTS.imageurl;

/**
 * Created by Prashant on 25-11-2017.
 */

    public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {
    View alertLayout1;
    private List<DataClass> objectList;
    private LayoutInflater inflater;
     ImageView imageView;


    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public EventsAdapter(Context context, List<DataClass> objectList) {
        inflater = LayoutInflater.from(context);
        this.objectList = objectList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.events_sample_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Context c;

        final DataClass current = objectList.get(position);
//        holder.img.setImageResource(objectList.get(position).getImg());
        holder.setData(current, position);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                v.findViewById(R.id.read).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inflateDescription(v.getContext(), String.valueOf(current.getDelId()), String.valueOf(current.getImageUrl()));
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemClickListener itemClickListener;
        ImageView img;
        TextView title, date, organiser, venue;
        DataClass currentObject;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.date = (TextView) itemView.findViewById(R.id.date);
            this.organiser = (TextView) itemView.findViewById(R.id.organiser);
            this.venue = (TextView) itemView.findViewById(R.id.venue);
            this.img = (ImageView) itemView.findViewById(R.id.eventImage);
            itemView.setOnClickListener(this);

        }

        public void setData(DataClass currentObject, int position) {
            this.title.setText(currentObject.getTitle());
            this.date.setText(currentObject.getTime());
            this.organiser.setText(currentObject.getOrganiser());
            this.venue.setText(currentObject.getVenue());
            this.img.setImageResource((currentObject.getImg()));
            this.currentObject = currentObject;
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }
    }

    public void inflateDescription(final Context c, String pos, final String url)
    {
        LayoutInflater inflater = LayoutInflater.from(c);
        alertLayout1 = inflater.inflate(R.layout.event_click_frag, null);

        final TextView desc = alertLayout1.findViewById(R.id.tvDesc);
        final TextView link = alertLayout1.findViewById(R.id.tvLink);
        final TextView mobNo = alertLayout1.findViewById(R.id.tvMobno);

        DatabaseReference descRef = database.getReference("EventThings").child(pos).child("desc");
        DatabaseReference linkRef = database.getReference("EventThings").child(pos).child("link");
        DatabaseReference mobNoRef = database.getReference("EventThings").child(pos).child("mobNo");

        imageView = (ImageView) alertLayout1.findViewById(R.id.imageEvent);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(alertLayout1.getContext(), FullScreenImage.class);
                intent.putExtra("url", url);
                alertLayout1.getContext().startActivity(intent);
            }
        });


        final View thumb1View = alertLayout1.findViewById(R.id.imageEvent);
        Picasso.with(c).load(url).into((ImageView) thumb1View);
//        thumb1View.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                zoomImageFromThumb(thumb1View, R.drawable.ic_launcher_background);
//            }
//        });
//
//        // Retrieve and cache the system's default "short" animation time.
//        mShortAnimationDuration = appCompatActivity.getResources().getInteger(
//                android.R.integer.config_shortAnimTime);

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                mAttacher = new PhotoViewAttacher(imageView);
////               mAttacher.update();
//                Intent intent= new Intent(c,FullScreenImage.class);
//                intent.putExtra("image",url);
//                c.startActivity(intent);
//
//            }
//        });


//        Intent intent= new Intent(c,FullScreenImage.class);
//        intent.putExtra("image",url);
//        c.startActivity(intent);


        descRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String d = dataSnapshot.getValue(String.class);
                desc.setText(d);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        linkRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String l = dataSnapshot.getValue(String.class);
                link.setText(l);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        mobNoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String m = dataSnapshot.getValue(String.class);
                mobNo.setText(m);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        AlertDialog.Builder alert1 = new AlertDialog.Builder(c);
        alert1.setTitle("Event-Description");
        alert1.setView(alertLayout1);
        alert1.setCancelable(true);

        AlertDialog dialog = alert1.create();
        dialog.show();
    }

//    private void zoomImageFromThumb(final View thumbView, int imageResId) {
//        // If there's an animation in progress, cancel it
//        // immediately and proceed with this one.
//        if (mCurrentAnimator != null) {
//            mCurrentAnimator.cancel();
//        }
//
//        // Load the high-resolution "zoomed-in" image.
//        final ImageView expandedImageView = (ImageView) alertLayout1.findViewById(
//                R.id.expanded_image);
//        expandedImageView.setImageResource(imageResId);
//
//        // Calculate the starting and ending bounds for the zoomed-in image.
//        // This step involves lots of math. Yay, math.
//        final Rect startBounds = new Rect();
//        final Rect finalBounds = new Rect();
//        final Point globalOffset = new Point();
//
//        // The start bounds are the global visible rectangle of the thumbnail,
//        // and the final bounds are the global visible rectangle of the container
//        // view. Also set the container view's offset as the origin for the
//        // bounds, since that's the origin for the positioning animation
//        // properties (X, Y).
//        thumbView.getGlobalVisibleRect(startBounds);
//        alertLayout1.findViewById(R.id.container)
//                .getGlobalVisibleRect(finalBounds, globalOffset);
//        startBounds.offset(-globalOffset.x, -globalOffset.y);
//        finalBounds.offset(-globalOffset.x, -globalOffset.y);
//
//        // Adjust the start bounds to be the same aspect ratio as the final
//        // bounds using the "center crop" technique. This prevents undesirable
//        // stretching during the animation. Also calculate the start scaling
//        // factor (the end scaling factor is always 1.0).
//        float startScale;
//        if ((float) finalBounds.width() / finalBounds.height()
//                > (float) startBounds.width() / startBounds.height()) {
//            // Extend start bounds horizontally
//            startScale = (float) startBounds.height() / finalBounds.height();
//            float startWidth = startScale * finalBounds.width();
//            float deltaWidth = (startWidth - startBounds.width()) / 2;
//            startBounds.left -= deltaWidth;
//            startBounds.right += deltaWidth;
//        } else {
//            // Extend start bounds vertically
//            startScale = (float) startBounds.width() / finalBounds.width();
//            float startHeight = startScale * finalBounds.height();
//            float deltaHeight = (startHeight - startBounds.height()) / 2;
//            startBounds.top -= deltaHeight;
//            startBounds.bottom += deltaHeight;
//        }
//
//        // Hide the thumbnail and show the zoomed-in view. When the animation
//        // begins, it will position the zoomed-in view in the place of the
//        // thumbnail.
//        thumbView.setAlpha(0f);
//        expandedImageView.setVisibility(View.VISIBLE);
//
//        // Set the pivot point for SCALE_X and SCALE_Y transformations
//        // to the top-left corner of the zoomed-in view (the default
//        // is the center of the view).
//        expandedImageView.setPivotX(0f);
//        expandedImageView.setPivotY(0f);
//
//        // Construct and run the parallel animation of the four translation and
//        // scale properties (X, Y, SCALE_X, and SCALE_Y).
//        AnimatorSet set = new AnimatorSet();
//        set
//                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
//                        startBounds.left, finalBounds.left))
//                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
//                        startBounds.top, finalBounds.top))
//                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
//                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
//                View.SCALE_Y, startScale, 1f));
//        set.setDuration(mShortAnimationDuration);
//        set.setInterpolator(new DecelerateInterpolator());
//        set.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mCurrentAnimator = null;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                mCurrentAnimator = null;
//            }
//        });
//        set.start();
//        mCurrentAnimator = set;
//
//        // Upon clicking the zoomed-in image, it should zoom back down
//        // to the original bounds and show the thumbnail instead of
//        // the expanded image.
//        final float startScaleFinal = startScale;
//        expandedImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mCurrentAnimator != null) {
//                    mCurrentAnimator.cancel();
//                }
//
//                // Animate the four positioning/sizing properties in parallel,
//                // back to their original values.
//                AnimatorSet set = new AnimatorSet();
//                set.play(ObjectAnimator
//                        .ofFloat(expandedImageView, View.X, startBounds.left))
//                        .with(ObjectAnimator
//                                .ofFloat(expandedImageView,
//                                        View.Y,startBounds.top))
//                        .with(ObjectAnimator
//                                .ofFloat(expandedImageView,
//                                        View.SCALE_X, startScaleFinal))
//                        .with(ObjectAnimator
//                                .ofFloat(expandedImageView,
//                                        View.SCALE_Y, startScaleFinal));
//                set.setDuration(mShortAnimationDuration);
//                set.setInterpolator(new DecelerateInterpolator());
//                set.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        thumbView.setAlpha(1f);
//                        expandedImageView.setVisibility(View.GONE);
//                        mCurrentAnimator = null;
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//                        thumbView.setAlpha(1f);
//                        expandedImageView.setVisibility(View.GONE);
//                        mCurrentAnimator = null;
//                    }
//                });
//                set.start();
//                mCurrentAnimator = set;
//            }
//        });
//    }
}

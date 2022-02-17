package ir.es.mohammad.netflix

import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.setMargins
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView

class MovieRecyclerAdapter(private val movies: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieRecyclerAdapter.CustomViewHolder>() {

    var setEachItem: CustomViewHolder.(movie: Movie) -> Unit = { movie ->
        movieTitle.text = movie.title
        actionImgBtn.visibility = View.GONE
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImg: ImageView = itemView.findViewById(R.id.imageMovieImg)
        val actionImgBtn: ImageButton = itemView.findViewById(R.id.btnMovieAction)
        val movieTitle: TextView = itemView.findViewById(R.id.textMovieName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie, parent, false)
        view.layoutParams.height = RecyclerView.LayoutParams.WRAP_CONTENT
        view.updateLayoutParams<RecyclerView.LayoutParams> { setMargins((8).toPx) }
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.setEachItem(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun remove(actualPosition: Int){
        movies.removeAt(actualPosition)
        notifyItemRemoved(actualPosition);
    }

    private val Number.toPx
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
}
package evtomak.iu.edu.fooddelivery

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import java.util.*

// DeliveryService: A background service for simulating food delivery with a timer.
class DeliveryService : Service() {

    companion object {
        const val EXTRA_DELIVERY_TIME = "extra_delivery_time"
    }

    private lateinit var deliveryHandler: Handler

    override fun onCreate() {
        super.onCreate()
        deliveryHandler = Handler(Looper.getMainLooper())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.hasExtra(EXTRA_DELIVERY_TIME)) {
            val deliveryTimeMillis = intent.getLongExtra(EXTRA_DELIVERY_TIME, 0)
            if (deliveryTimeMillis > 0) {
                // Schedule a delivery completion task after the specified delivery time.
                deliveryHandler.postDelayed({
                    Log.i("DeliveryService", "Delivery time is over, generate a notification")
                    stopSelf() // Stop the service after delivery completion.
                }, deliveryTimeMillis)
            } else {
                Log.e("DeliveryService", "Invalid delivery time")
                stopSelf() // Stop the service due to an error.
            }
        } else {
            Log.e("DeliveryService", "No delivery time provided")
            stopSelf() // Stop the service if no delivery time is provided.
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}

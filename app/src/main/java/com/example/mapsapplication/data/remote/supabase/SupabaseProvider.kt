package com.example.mapsapplication.data.remote.supabase

import com.example.mapsapplication.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseProvider {

    val client = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL, supabaseKey = BuildConfig.SUPABASE_KEY
    ) {
        install(Postgrest)
    }
}
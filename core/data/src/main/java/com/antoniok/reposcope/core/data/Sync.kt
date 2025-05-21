package com.antoniok.reposcope.core.data

/**
 * Interface representing a synchronization contract.
 *
 * Classes implementing this interface are responsible for synchronizing data
 * from a specified source (e.g., a remote server) based on the provided organization name.
 */
public interface Sync {

   /**
    * Synchronizes data related to the given organization.
    *
    * This operation typically fetches and updates local or cached data to reflect
    * the current state from a remote source (e.g., GitHub organization data).
    *
    * @param org The name of the organization whose data should be synchronized.
    * @return `true` if the synchronization was successful, `false` otherwise.
    */
   public suspend fun sync(org: String): Boolean
}

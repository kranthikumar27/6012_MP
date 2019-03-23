/*
 * Copyright (C) 2016 The Android Open Source Project
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
package com.example.android.background.sync;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class WaterReminderFirebaseJobService {
    // done (3) WaterReminderFirebaseJobService should extend from JobService
    public class WaterReminderFirebaseJobService extends JobService {

        private AsyncTask mBackgroundTask;

        // done (4) Override onStartJob
        @Override
        public boolean onStartJob(final JobParameters jobParameters) {

            // done (5) By default, jobs are executed on the main thread, so make an anonymous class extending
            //  AsyncTask called mBackgroundTask.
            mBackgroundTask = new AsyncTask() {

                // done (6) Override doInBackground
                @Override
                protected Object doInBackground(Object[] params) {
                    // done (7) Use ReminderTasks to execute the new charging reminder task you made, use
                    // this service as the context (WaterReminderFirebaseJobService.this) and return null
                    // when finished.
                    Context context = WaterReminderFirebaseJobService.this;
                    ReminderTasks.executeTask(context, ReminderTasks.ACTION_CHARGING_REMINDER);
                    return null;
                }

                // done (8) Override onPostExecute and call jobFinished. Pass the job parameters
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                protected void onPostExecute(Object o) {
                    // and false to jobFinished. This will inform the JobManager that your job is done
                    // and that you do not want to reschedule the job.
                    jobFinished(jobParameters, false);
                }
            };
            // done (9) Execute the AsyncTask
            // done (10) Return true
            mBackgroundTask.execute();
            return true;
        }

        // done (11) Override onStopJob
        public boolean onStopJob(JobParameters jobParameters) {
            // done (12) If mBackgroundTask is valid, cancel it
            // done (13) Return true to signify the job should be retried
            if (mBackgroundTask != null) mBackgroundTask.cancel(true);
            return true;
        }
    }
    // done (12) If mBackgroundTask is valid, cancel it
    // done (13) Return true to signify the job should be retried

}

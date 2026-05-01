package cgeo.geocaching;

import cgeo.geocaching.activity.AbstractActivity;
import cgeo.geocaching.settings.Settings;
import cgeo.geocaching.storage.ContentStorageActivityHelper;
import cgeo.geocaching.storage.extension.OneTimeDialogs;
import cgeo.geocaching.utils.ContextLogger;
import cgeo.geocaching.utils.FileUtils;
import cgeo.geocaching.utils.LocalizationUtils;
import cgeo.geocaching.utils.Log;
import cgeo.geocaching.utils.ProcessUtils;
import cgeo.geocaching.utils.TextUtils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SplashActivity extends AbstractActivity {

    private static final long ANIM_TOTAL_MS = 2200L;
    private static final long ZOOM_DURATION_MS = 1500L;
    private static final long CROSSFADE_DELAY_MS = 700L;
    private static final long CROSSFADE_DURATION_MS = 800L;
    private static final float ZOOM_SCALE = 2.5f;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_prank);

        final View steak = findViewById(R.id.splash_steak);
        final View face = findViewById(R.id.splash_face);

        final ObjectAnimator steakScaleX = ObjectAnimator.ofFloat(steak, "scaleX", 1f, ZOOM_SCALE);
        final ObjectAnimator steakScaleY = ObjectAnimator.ofFloat(steak, "scaleY", 1f, ZOOM_SCALE);
        final ObjectAnimator faceScaleX = ObjectAnimator.ofFloat(face, "scaleX", 1f, ZOOM_SCALE);
        final ObjectAnimator faceScaleY = ObjectAnimator.ofFloat(face, "scaleY", 1f, ZOOM_SCALE);
        steakScaleX.setDuration(ZOOM_DURATION_MS);
        steakScaleY.setDuration(ZOOM_DURATION_MS);
        faceScaleX.setDuration(ZOOM_DURATION_MS);
        faceScaleY.setDuration(ZOOM_DURATION_MS);

        final ObjectAnimator steakFade = ObjectAnimator.ofFloat(steak, "alpha", 1f, 0f);
        steakFade.setStartDelay(CROSSFADE_DELAY_MS);
        steakFade.setDuration(CROSSFADE_DURATION_MS);

        final ObjectAnimator faceFade = ObjectAnimator.ofFloat(face, "alpha", 0f, 1f);
        faceFade.setStartDelay(CROSSFADE_DELAY_MS);
        faceFade.setDuration(CROSSFADE_DURATION_MS);

        final AnimatorSet set = new AnimatorSet();
        set.playTogether(steakScaleX, steakScaleY, faceScaleX, faceScaleY, steakFade, faceFade);
        set.setStartDelay(150L); // brief blank moment so the user sees the start, not a mid-animation flash
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                proceedToOriginalDestination();
            }
        });
        set.start();

        // safety net: if anything kills the AnimatorSet listener (e.g. window detach), still proceed
        steak.postDelayed(this::proceedToOriginalDestination, ANIM_TOTAL_MS + 1000L);
    }

    private boolean proceeded = false;

    private void proceedToOriginalDestination() {
        if (proceeded) {
            return;
        }
        proceeded = true;

        try (ContextLogger cLog = new ContextLogger(Log.LogLevel.DEBUG, "SplashActivity.proceed")) {
            final Intent intent;
            final boolean firstInstall = Settings.getLastChangelogChecksum() == 0;
            final boolean folderMigrationNeeded = InstallWizardActivity.needsFolderMigration();
            if (firstInstall || !ContentStorageActivityHelper.baseFolderIsSet() || folderMigrationNeeded) {
                intent = new Intent(this, InstallWizardActivity.class);
                intent.putExtra(InstallWizardActivity.BUNDLE_MODE, firstInstall ? InstallWizardActivity.WizardMode.WIZARDMODE_DEFAULT.id : InstallWizardActivity.WizardMode.WIZARDMODE_MIGRATION.id);
            } else {
                intent = Settings.getStartscreenIntent(this);
                intent.putExtras(getIntent());
            }
            cLog.add("fi");

            OneTimeDialogs.nextStatus();
            cLog.add("otd");

            startActivity(intent);
            cLog.add("sa");

            checkChangedInstall();
            cLog.add("cci");

            finish();
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    private void checkChangedInstall() {
        // temporary workaround for #4143
        //TODO: understand and avoid if possible
        try {
            final long lastChecksum = Settings.getLastChangelogChecksum();
            final long checksum = TextUtils.checksum(FileUtils.getChangelogMaster(this) + FileUtils.getChangelogRelease(this));
            Settings.setLastChangelogChecksum(checksum);

            if (lastChecksum == 0) {
                // initialize oneTimeMessages after fresh install
                OneTimeDialogs.initializeOnFreshInstall();
                // initialize useInternalRouting setting depending on whether BRouter app is installed or not
                Settings.setUseInternalRouting(!ProcessUtils.isInstalled(LocalizationUtils.getPlainString(R.string.package_brouter)));
            } else if (lastChecksum != checksum) {
                // show change log page after update
                AboutActivity.showChangeLog(this);
            }
        } catch (final Exception ex) {
            Log.e("Error checking/showing changelog!", ex);
        }
    }
}

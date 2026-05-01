package cgeo.geocaching.unifiedmap.tileproviders;

import cgeo.geocaching.R;
import cgeo.geocaching.settings.Settings;
import cgeo.geocaching.utils.LocalizationUtils;

import android.net.Uri;

import androidx.core.util.Pair;

import org.apache.commons.lang3.StringUtils;

abstract class AbstractMapyCzSource extends AbstractMapsforgeOnlineTileProvider {

    AbstractMapyCzSource(final String displayName, final String layer, final int zoomMax) {
        super(displayName,
                Uri.parse("https://api.mapy.cz/v1/maptiles/" + layer + "/256"),
                "/{Z}/{X}/{Y}?apikey=" + StringUtils.defaultString(Settings.getMapyCzApiKey()),
                2, zoomMax,
                new Pair<>(LocalizationUtils.getPlainString(R.string.map_attribution_mapycz_html), true));
    }

    static boolean isConfigured() {
        return Settings.useMapyCz() && StringUtils.isNotBlank(Settings.getMapyCzApiKey());
    }
}

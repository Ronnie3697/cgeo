package cgeo.geocaching.unifiedmap.tileproviders;

import cgeo.geocaching.R;
import cgeo.geocaching.utils.LocalizationUtils;

class MapyCzWinterSource extends AbstractMapyCzSource {
    MapyCzWinterSource() {
        super(LocalizationUtils.getPlainString(R.string.map_source_mapycz_winter), "winter", 18);
    }
}

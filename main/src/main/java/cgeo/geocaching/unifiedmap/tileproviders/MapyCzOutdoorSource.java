package cgeo.geocaching.unifiedmap.tileproviders;

import cgeo.geocaching.R;
import cgeo.geocaching.utils.LocalizationUtils;

class MapyCzOutdoorSource extends AbstractMapyCzSource {
    MapyCzOutdoorSource() {
        super(LocalizationUtils.getPlainString(R.string.map_source_mapycz_outdoor), "outdoor", 18);
    }
}

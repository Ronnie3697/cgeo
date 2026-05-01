package cgeo.geocaching.unifiedmap.tileproviders;

import cgeo.geocaching.R;
import cgeo.geocaching.utils.LocalizationUtils;

class MapyCzBasicSource extends AbstractMapyCzSource {
    MapyCzBasicSource() {
        super(LocalizationUtils.getPlainString(R.string.map_source_mapycz_basic), "basic", 19);
    }
}

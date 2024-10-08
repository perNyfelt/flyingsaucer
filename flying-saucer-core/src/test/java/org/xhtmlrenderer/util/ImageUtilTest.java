package org.xhtmlrenderer.util;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static java.awt.Transparency.OPAQUE;
import static java.awt.Transparency.TRANSLUCENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.xhtmlrenderer.util.ImageUtil.detectTransparency;
import static org.xhtmlrenderer.util.ImageUtil.isEmbeddedBase64Image;
import static org.xhtmlrenderer.util.ImageUtil.loadEmbeddedBase64Image;

class ImageUtilTest {
    @Test
    void embeddedImageUrl() {
        assertThat(isEmbeddedBase64Image(null)).isFalse();
        assertThat(isEmbeddedBase64Image("")).isFalse();
        assertThat(isEmbeddedBase64Image("https://selenide.org/images/selenide-logo-big.png")).isFalse();
        assertThat(isEmbeddedBase64Image("data:image/png;base64,iVBORw0KG...")).isTrue();
        assertThat(isEmbeddedBase64Image("data:image/png;base64, iVBORw0KGgo...")).isTrue();
        assertThat(isEmbeddedBase64Image("data:image/; base64,iVBORw0KG...")).isTrue();
        assertThat(isEmbeddedBase64Image("data:image/;base64,iVBORw0KG...")).isTrue();
        assertThat(isEmbeddedBase64Image("data:image;base64,iVBORw0KG...")).isTrue();
        assertThat(isEmbeddedBase64Image("data:image/svg+xml;utf8,<svg xmlns='....")).isTrue();
        assertThat(isEmbeddedBase64Image("data:image/svg;utf8,<svg xmlns='....")).isTrue();
    }

    @Test
    void embeddedBase64Image() {
        BufferedImage image = loadEmbeddedBase64Image("data:image/png;base64,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");
        assertThat(image).isNotNull();
        assertThat(image.getWidth()).isEqualTo(352);
        assertThat(image.getHeight()).isEqualTo(186);
    }

    @Test
    void detectsImageType() {
        assertThat(detectTransparency(BufferedImage.TYPE_INT_ARGB)).isEqualTo(TRANSLUCENT);
        assertThat(detectTransparency(BufferedImage.TYPE_INT_ARGB_PRE)).isEqualTo(TRANSLUCENT);
        assertThat(detectTransparency(BufferedImage.TYPE_4BYTE_ABGR)).isEqualTo(TRANSLUCENT);
        assertThat(detectTransparency(BufferedImage.TYPE_4BYTE_ABGR_PRE)).isEqualTo(TRANSLUCENT);

        assertThat(detectTransparency(BufferedImage.TYPE_3BYTE_BGR)).isEqualTo(OPAQUE);
        assertThat(detectTransparency(BufferedImage.TYPE_CUSTOM)).isEqualTo(OPAQUE);
        assertThat(detectTransparency(BufferedImage.TYPE_INT_RGB)).isEqualTo(OPAQUE);
    }
}
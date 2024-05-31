/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.common.network.codec;

import com.mojang.datafixers.util.Function7;
import com.mojang.datafixers.util.Function8;
import com.mojang.datafixers.util.Function9;

import java.util.function.Function;

public abstract class PacketCodec implements net.minecraft.network.codec.PacketCodec {
	public static <B, C, T1, T2, T3, T4, T5, T6, T7> net.minecraft.network.codec.PacketCodec<B, C> tuple(final net.minecraft.network.codec.PacketCodec<? super B, T1> codec1, final Function<C, T1> from1, final net.minecraft.network.codec.PacketCodec<? super B, T2> codec2, final Function<C, T2> from2, final net.minecraft.network.codec.PacketCodec<? super B, T3> codec3, final Function<C, T3> from3, final net.minecraft.network.codec.PacketCodec<? super B, T4> codec4, final Function<C, T4> from4, final net.minecraft.network.codec.PacketCodec<? super B, T5> codec5, final Function<C, T5> from5, final net.minecraft.network.codec.PacketCodec<? super B, T6> codec6, final Function<C, T6> from6, final net.minecraft.network.codec.PacketCodec<? super B, T7> codec7, final Function<C, T7> from7, final Function7<T1, T2, T3, T4, T5, T6, T7, C> to) {
		return new net.minecraft.network.codec.PacketCodec<B, C>() {
			public C decode(B object) {
				T1 object2 = codec1.decode(object);
				T2 object3 = codec2.decode(object);
				T3 object4 = codec3.decode(object);
				T4 object5 = codec4.decode(object);
				T5 object6 = codec5.decode(object);
				T6 object7 = codec6.decode(object);
				T7 object8 = codec7.decode(object);
				return to.apply(object2, object3, object4, object5, object6, object7, object8);
			}
			public void encode(B object, C object2) {
				codec1.encode(object, from1.apply(object2));
				codec2.encode(object, from2.apply(object2));
				codec3.encode(object, from3.apply(object2));
				codec4.encode(object, from4.apply(object2));
				codec5.encode(object, from5.apply(object2));
				codec6.encode(object, from6.apply(object2));
				codec7.encode(object, from7.apply(object2));
			}
		};
	}
	public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8> net.minecraft.network.codec.PacketCodec<B, C> tuple(final net.minecraft.network.codec.PacketCodec<? super B, T1> codec1, final Function<C, T1> from1, final net.minecraft.network.codec.PacketCodec<? super B, T2> codec2, final Function<C, T2> from2, final net.minecraft.network.codec.PacketCodec<? super B, T3> codec3, final Function<C, T3> from3, final net.minecraft.network.codec.PacketCodec<? super B, T4> codec4, final Function<C, T4> from4, final net.minecraft.network.codec.PacketCodec<? super B, T5> codec5, final Function<C, T5> from5, final net.minecraft.network.codec.PacketCodec<? super B, T6> codec6, final Function<C, T6> from6, final net.minecraft.network.codec.PacketCodec<? super B, T7> codec7, final Function<C, T7> from7, final net.minecraft.network.codec.PacketCodec<? super B, T8> codec8, final Function<C, T8> from8, final Function8<T1, T2, T3, T4, T5, T6, T7, T8, C> to) {
		return new net.minecraft.network.codec.PacketCodec<B, C>() {
			public C decode(B object) {
				T1 object2 = codec1.decode(object);
				T2 object3 = codec2.decode(object);
				T3 object4 = codec3.decode(object);
				T4 object5 = codec4.decode(object);
				T5 object6 = codec5.decode(object);
				T6 object7 = codec6.decode(object);
				T7 object8 = codec7.decode(object);
				T8 object9 = codec8.decode(object);
				return to.apply(object2, object3, object4, object5, object6, object7, object8, object9);
			}
			public void encode(B object, C object2) {
				codec1.encode(object, from1.apply(object2));
				codec2.encode(object, from2.apply(object2));
				codec3.encode(object, from3.apply(object2));
				codec4.encode(object, from4.apply(object2));
				codec5.encode(object, from5.apply(object2));
				codec6.encode(object, from6.apply(object2));
				codec7.encode(object, from7.apply(object2));
				codec8.encode(object, from8.apply(object2));
			}
		};
	}
	public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9> net.minecraft.network.codec.PacketCodec<B, C> tuple(final net.minecraft.network.codec.PacketCodec<? super B, T1> codec1, final Function<C, T1> from1, final net.minecraft.network.codec.PacketCodec<? super B, T2> codec2, final Function<C, T2> from2, final net.minecraft.network.codec.PacketCodec<? super B, T3> codec3, final Function<C, T3> from3, final net.minecraft.network.codec.PacketCodec<? super B, T4> codec4, final Function<C, T4> from4, final net.minecraft.network.codec.PacketCodec<? super B, T5> codec5, final Function<C, T5> from5, final net.minecraft.network.codec.PacketCodec<? super B, T6> codec6, final Function<C, T6> from6, final net.minecraft.network.codec.PacketCodec<? super B, T7> codec7, final Function<C, T7> from7, final net.minecraft.network.codec.PacketCodec<? super B, T8> codec8, final Function<C, T8> from8, final net.minecraft.network.codec.PacketCodec<? super B, T9> codec9, final Function<C, T9> from9, final Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, C> to) {
		return new net.minecraft.network.codec.PacketCodec<B, C>() {
			public C decode(B object) {
				T1 object2 = codec1.decode(object);
				T2 object3 = codec2.decode(object);
				T3 object4 = codec3.decode(object);
				T4 object5 = codec4.decode(object);
				T5 object6 = codec5.decode(object);
				T6 object7 = codec6.decode(object);
				T7 object8 = codec7.decode(object);
				T8 object9 = codec8.decode(object);
				T9 object10 = codec9.decode(object);
				return to.apply(object2, object3, object4, object5, object6, object7, object8, object9, object10);
			}
			public void encode(B object, C object2) {
				codec1.encode(object, from1.apply(object2));
				codec2.encode(object, from2.apply(object2));
				codec3.encode(object, from3.apply(object2));
				codec4.encode(object, from4.apply(object2));
				codec5.encode(object, from5.apply(object2));
				codec6.encode(object, from6.apply(object2));
				codec7.encode(object, from7.apply(object2));
				codec8.encode(object, from8.apply(object2));
				codec9.encode(object, from9.apply(object2));
			}
		};
	}
}

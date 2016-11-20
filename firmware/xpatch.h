extern "C" {
#include <patch.h>
#include <axoloti.h>
#include <parameter_functions.h>
#include <axoloti_memory.h>
}

#include "arm_intrinsics.hpp"

#include "braids/macro_oscillator.h"
#include "rings/dsp/string.h"
#include "rings/dsp/resonator.h"
#include "rings/dsp/fx/chorus.h"
#include "rings/dsp/fx/ensemble.h"
#include "rings/dsp/fx/reverb.h"
#include "warps/dsp/vocoder.h"
#include "elements/dsp/exciter.h"
#include "elements/dsp/tube.h"
#include "elements/dsp/string.h"
#include "elements/dsp/fx/diffuser.h"
#include "elements/dsp/fx/reverb.h"
#include "clouds/dsp/fx/pitch_shifter.h"
#include "elements/dsp/part.h"
#include "elements/dsp/patch.h"
